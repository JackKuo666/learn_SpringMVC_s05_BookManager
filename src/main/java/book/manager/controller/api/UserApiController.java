package book.manager.controller.api;

import book.manager.entity.AuthUser;
import book.manager.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/user")
public class UserApiController {

    @Resource
    BookService bookService;

    @RequestMapping("/borrow-book")
    public String borrowBook(@RequestParam("id") int bid,
                             @SessionAttribute("user") AuthUser user){
                            // 注意这里需要userId,因为已经登录，所以通过session获取，
        // 另外因为是entity、mapper、service、controller分层，所以在controller层获取是最合适的
        boolean result = bookService.borrowBook(bid, user.getId());
        return "redirect:/page/user/book";
    }

    @RequestMapping("/return-book")
    public String returnBook(@RequestParam("id") int bid,
                             @SessionAttribute("user") AuthUser user){
                            // 注意这里需要userId,因为已经登录，所以通过session获取，
        // 另外因为是entity、mapper、service、controller分层，所以在controller层获取是最合适的
        bookService.returnBook(bid, user.getId());
        return "redirect:/page/user/book";
    }
}
