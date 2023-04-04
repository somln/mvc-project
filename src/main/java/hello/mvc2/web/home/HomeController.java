package hello.mvc2.web.home;

import hello.mvc2.domain.member.Member;
import hello.mvc2.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


@Controller
public class HomeController {
    @GetMapping("/")
    public String home(@Login Member member,
                       Model model) {

        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }
}
