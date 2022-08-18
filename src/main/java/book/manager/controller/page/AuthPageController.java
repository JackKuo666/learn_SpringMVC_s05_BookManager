package book.manager.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 专用于处理页面响应的控制器
 */
@Controller
@RequestMapping("/page/auth")
public class AuthPageController {

    @RequestMapping(value = "/login", produces = "text/html;charset=utf-8")
    public  String login(){
        return "login";
    }

    @RequestMapping(value = "/register", produces = "text/html;charset=utf-8")
    public String register(){
        return "register";
    }
}