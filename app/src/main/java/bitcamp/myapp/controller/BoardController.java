package bitcamp.myapp.controller;

import bitcamp.myapp.annotation.LoginUser;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.service.StorageService;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.*;
import java.util.*;

/**
 * packageName    : bitcamp.myapp.servlet.board
 * fileName       : BoardAddServlet
 * author         : narilee
 * date           : 24. 8. 28.
 * description    : 게시글 등록을 처리하는 서블릿 클래스입니다. 이 서블릿은 사용자가 입력한 게시글 정보를 받아 데이터베이스에 저장하고, 처리 결과를 HTML 형식으로 응답합니다.
 * ===========================================================
 * DATE              AUTHOR         NOTE
 * -----------------------------------------------------------
 * 24. 8. 28.        narilee       최초 생성
 * 24. 9. 05.        narilee       HttpServlet으로 변경
 * 24. 9. 09.        narilee       UTF-8 필터 적용, 첨부파일 적용
 * 24. 9. 11.        narilee       BoardService 적용
 * 24. 9. 12.        narilee       DispatcherServlet 적용
 * 24. 9. 19.        narilee       HttpServletResponse 삭제, Param 변경
 * 24. 9. 23.        narilee       @Controller 적용
 * 24. 9. 25.        narilee       Spring 도입
 * 24. 9. 27.        narilee       ncloud 도입
 * 24. 10. 21.       narilee       page limit 도입
 * 24. 10. 21.       narilee       lombok 적용
 * 24. 10. 24.       narilee
 */
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;
  private final StorageService storageService;

  private String folderName = "board/";

  @GetMapping("form")
  public void form() {
  }

  @PostMapping("add")
  public String add(
      Board board,
      MultipartFile[] files,
      @LoginUser User loginUser
      ) throws Exception {

    if (loginUser == null) {
      throw new Exception("로그인 하지 않았습니다.");
    }

    board.setWriter(loginUser);

    ArrayList<AttachedFile> attachedFiles = new ArrayList<>();

    for (MultipartFile file : files) {
      if (file.getSize() == 0) {
        continue;
      }

      AttachedFile attachedFile = new AttachedFile();
      attachedFile.setFilename(UUID.randomUUID().toString());
      attachedFile.setOriginFilename(file.getOriginalFilename());

      // 첨부 파일을 Object Storage에 올린다.
      HashMap<String, Object> options = new HashMap<>();
      options.put(StorageService.CONTENT_TYPE, file.getContentType());
      storageService.upload(folderName + attachedFile.getFilename(),
          file.getInputStream(),
          options);

      attachedFiles.add(attachedFile);
    }

    board.setAttachedFiles(attachedFiles);

    boardService.add(board);
    return "redirect:list";
  }

  @GetMapping("list")
  public void list(
      @RequestParam(defaultValue = "1") int pageNo,
      @RequestParam(defaultValue = "3") int pageSize,
      Model model) throws Exception {

    if (pageNo < 1) {
      pageNo = 1;
    }

    int length = boardService.countAll();

    int pageCount = length / pageSize;
    if (length % pageSize > 0) {
      pageCount++;
    }

    if (pageNo > pageCount) {
      pageNo = pageCount;
    }

    List<Board> list = boardService.list(pageNo, pageSize);
    model.addAttribute("list", list);
    model.addAttribute("pageNo", pageNo);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("pageCount", pageCount);
  }

  @GetMapping("view")
  public void view(int no, Model model) throws Exception {
    Board board = boardService.get(no);
    if (board == null) {
      throw new Exception("게시글이 존재하지 않습니다.");
    }

    boardService.increaseViewCount(board.getNo());

    model.addAttribute("board", board);
  }

  @PostMapping("update")
  public String update(
      int no,
      String title,
      String content,
      Part[] files,
      HttpSession session) throws Exception {

    User loginUser = (User) session.getAttribute("loginUser");

    Board board = boardService.get(no);
    if (board == null) {
      throw new Exception("없는 게시글입니다.");
    } else if (loginUser == null || loginUser.getNo() > 10 && board.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("변경 권한이 없습니다.");
    }

    board.setTitle(title);
    board.setContent(content);

    ArrayList<AttachedFile> attachedFiles = new ArrayList<>();

    for (Part part : files) {
      if (part.getSize() == 0) {
        continue;
      }

      AttachedFile attachedFile = new AttachedFile();
      attachedFile.setFilename(UUID.randomUUID().toString());
      attachedFile.setOriginFilename(part.getSubmittedFileName());

      // 첨부 파일을 Object Storage에 올린다.
      HashMap<String, Object> options = new HashMap<>();
      options.put(StorageService.CONTENT_TYPE, part.getContentType());
      storageService.upload(folderName + attachedFile.getFilename(),
          part.getInputStream(),
          options);

      attachedFiles.add(attachedFile);
    }

    board.setAttachedFiles(attachedFiles);

    boardService.update(board);
    return "redirect:list";
  }

  @GetMapping("delete")
  public String delete(
      int no,
      HttpSession session) throws Exception {

    User loginUser = (User) session.getAttribute("loginUser");
    Board board = boardService.get(no);

    if (board == null) {
      throw new Exception("없는 게시글입니다.");
    } else if (loginUser == null || loginUser.getNo() > 10 && board.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("삭제 권한이 없습니다.");
    }

    for (AttachedFile attachedFile : board.getAttachedFiles()) {
      try {
        storageService.delete(folderName + attachedFile.getFilename());
      } catch (Exception e) {
        System.out.printf("%s 파일 삭제 실패!\n", folderName + attachedFile.getFilename());
      }
    }

    boardService.delete(no);
    return "redirect:list";
  }

  @GetMapping("file/delete")
  public String fileDelete(
      HttpSession session,
      int fileNo,
      int boardNo) throws Exception {

    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인 하지 않았습니다.");
    }

    AttachedFile attachedFile = boardService.getAttachedFile(fileNo);
    if (attachedFile == null) {
      throw new Exception("없는 첨부파일입니다.");
    }

    Board board = boardService.get(attachedFile.getBoardNo());
    if (loginUser.getNo() > 10 && board.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("삭제 권한이 없습니다.");
    }

    try {
      storageService.delete(folderName + attachedFile.getFilename());
    } catch (Exception e) {
      System.out.printf("%s 파일 삭제 실패!\n", folderName + attachedFile.getFilename());
    }

    boardService.deleteAttachedFile(fileNo);
    return "redirect:../view?no=" + boardNo;
  }

}
