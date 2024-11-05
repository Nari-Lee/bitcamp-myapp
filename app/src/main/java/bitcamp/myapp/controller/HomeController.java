package bitcamp.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * packageName    : bitcamp.myapp.controller
 * fileName       : HomeController
 * author         : narilee
 * date           : 24. 9. 25.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 9. 25.        narilee       최초 생성
 */
@Controller
public class HomeController {

  @GetMapping("/home")
  public void home(Model model) {
    model.addAttribute("welcome", "<b>안녕</b>하세요!");
  }
}
