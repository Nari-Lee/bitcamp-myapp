package bitcamp.myapp.controller;

import bitcamp.myapp.service.StorageService;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * packageName    : bitcamp.myapp.controller
 * fileName       : UserController
 * author         : narilee
 * date           : 24. 8. 28.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 28.        narilee       최초 생성
 * 24. 8. 30.        narilee       수정
 * 24. 9. 05.        narilee       HttpServlet으로 변경
 * 24. 9. 09.        narilee       UTF-8 필터 적용
 * 24. 9. 11.        narilee       UserService 적용
 * 24. 9. 12.        narilee       DispatcherServlet 적용
 * 24. 9. 13.        narilee       Servlet을 Controller로 전환후 통합
 * 24. 9. 19.        narilee       HttpServletResponse 삭제, Param 변경
 * 24. 9. 23.        narilee       @Controller 적용
 * 24. 9. 25.        narilee       Spring 도입
 * 24. 9. 30.        narilee       Spring 도입
 * 24. 10. 17.       narilee       users 기본 url로 변경
 * 24. 10. 21.       narilee       lombok 적용
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private final StorageService storageService;

  private String folderName = "user/";

  @GetMapping("form")
  public String form() {
    return "user/form";
  }

  @PostMapping
  public String add(User user, MultipartFile file) throws Exception {

    // 클라이언트가 보낸 파일을 저장할 때 다른 파일 이름과 충돌나지 않도록 임의의 새 파일 이름을 생성한다.
    String filename = UUID.randomUUID().toString();

    HashMap<String, Object> options = new HashMap<>();
    options.put(StorageService.CONTENT_TYPE, file.getContentType());
    storageService.upload(folderName + filename,
        file.getInputStream(),
        options);

    user.setPhoto(filename); // DB에 저장할 사진 파일 이름 설정

    userService.add(user);
    return "redirect:../users";
  }

  @GetMapping
  public String list(Model model) throws Exception {
    List<User> list = userService.list();
    model.addAttribute("list", list);
    return "user/list";
  }

  @GetMapping("{no}")
  public String view(
      @PathVariable int no,
      Model model) throws Exception {
    User user = userService.get(no);
    model.addAttribute("user", user);
    return "user/view";
  }

  @GetMapping("myInfo")
  public String myInfo(
      HttpSession session,
      Model model) throws Exception {
    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인이 필요합니다.");
    }
    User user = userService.get(loginUser.getNo());
    model.addAttribute("user", user);
    return "user/view";
  }

  @PostMapping("{no}")
  public String update(
      @PathVariable int no,
      User user,
      MultipartFile file) throws Exception {

    user.setNo(no);

    User old = userService.get(no);

    if (file != null && file.getSize() > 0) {
      storageService.delete(folderName + old.getPhoto());

      String filename = UUID.randomUUID().toString();
      HashMap<String, Object> options = new HashMap<>();
      options.put(StorageService.CONTENT_TYPE, file.getContentType());
      storageService.upload(folderName + filename,
          file.getInputStream(),
          options);

      user.setPhoto(filename);

    } else {
      user.setPhoto(old.getPhoto());
    }

    if (userService.update(user)) {
      return "redirect:../users";
    } else {
      throw new Exception("없는 회원입니다!");
    }
  }

  @Transactional
  @DeleteMapping("{no}")
  @ResponseBody
  public String delete(
      @PathVariable int no) throws Exception {

    User old = userService.get(no);

    if (userService.delete(no)) {
      storageService.delete(folderName + old.getPhoto());
      return "success";
    } else {
      return "failure";
    }
  }
}