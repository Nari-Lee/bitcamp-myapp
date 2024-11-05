package bitcamp.myapp.controller;

import bitcamp.myapp.service.ProjectService;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : bitcamp.myapp.servlet.project
 * fileName       : ProjectAddServlet
 * author         : narilee
 * date           : 24. 8. 28.
 * description    : 프로젝트 등록을 처리하는 서블릿 클래스입니다.
 *                  이 서블릿은 사용자가 입력한 프로젝트 정보를 받아 데이터베이스에 저장하고,
 *                  처리 결과를 HTML 형식으로 응답합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 28.        narilee       최초 생성
 * 24. 9. 05.        narilee       HttpServlet으로 변경
 * 24. 9. 09.        narilee       UTF-8 필터 적용, 첨부파일 추가
 * 24. 9. 11.        narilee       projectService 적용
 * 24. 9. 12.        narilee       DispatcherServlet 적용
 * 24. 9. 19.        narilee       HttpServletResponse 삭제, Param 변경
 * 24. 9. 23.        narilee       @Controller 적용
 * 24. 9. 25.        narilee       Spring 도입
 * 24. 10. 21.       narilee       lombok 적용
 */
@Controller
@RequestMapping("/project")
@SessionAttributes("project")
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;
  private final UserService userService;

  @GetMapping("form1")
  public String form1() {
    return "project/form1";
  }

  @PostMapping("form2")
  public ModelAndView form2(Project project) throws Exception {
    List<User> users = userService.list();

    ModelAndView mv = new ModelAndView();
    mv.addObject("project", project);
    mv.addObject("users", users);
    mv.setViewName("project/form2");
    return mv;
  }

  @PostMapping("form3")
  public String form3(
      int[] memberNos,
      @ModelAttribute Project project) throws Exception {

    if (memberNos.length > 0) {
      ArrayList<User> members = new ArrayList<>();
      for (int memberNo : memberNos) {
        User user = userService.get(memberNo);
        members.add(user);
      }
      project.setMembers(members);
    }

    return "project/form3";
  }

  @PostMapping("add")
  public String add(@ModelAttribute Project project, SessionStatus sessionStatus) throws Exception {
    projectService.add(project);
    sessionStatus.setComplete(); // 작업이 끝났으니 세션에 임시 보관했던 값을 제거하라. @SessionAttributes 에 등록된 이름의 값.
    return "redirect:list";
  }

  @GetMapping("list")
  public ModelAndView list() throws Exception {
    List<Project> list = projectService.list();

    ModelAndView mv = new ModelAndView();
    mv.addObject("list", list);
    mv.setViewName("project/list");
    return mv;
  }

  @GetMapping("view")
  public ModelAndView view(int no) throws Exception {
    ModelAndView mv = new ModelAndView();

    Project project = projectService.get(no);
    mv.addObject("project", project);

    List<User> users = userService.list();
    mv.addObject("users", users);
    mv.setViewName("project/view");
    return mv;
  }

  @PostMapping("update")
  public String update(
      Project project,
      int[] memberNos) throws Exception {

    if (memberNos.length > 0) {
      ArrayList<User> members = new ArrayList<>();
      for (int memberNo : memberNos) {
        members.add(new User(memberNo));
      }
      project.setMembers(members);
    }

    if (!projectService.update(project)) {
      throw new Exception("없는 프로젝트입니다!");
    }
    return "redirect:list";
  }

  @GetMapping("delete")
  public String delete(int no) throws Exception {
    if (projectService.delete(no)) {
      return "redirect:list";
    } else {
      throw new Exception("없는 프로젝트입니다.");
    }
  }
}
