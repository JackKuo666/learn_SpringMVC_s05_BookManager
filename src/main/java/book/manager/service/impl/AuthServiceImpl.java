package book.manager.service.impl;

import book.manager.entity.AuthUser;
import book.manager.mapper.UserMapper;
import book.manager.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Transient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    UserMapper mapper;

    @Override
    @Transactional  // 添加事务管理：当前操作有错自动回滚；注意：需要在RootConfiguration.java中配置
    public void register(String name, String sex, String grade, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AuthUser user = new AuthUser(0, name, encoder.encode(password), "user");
        if (mapper.registerUser(user) <= 0)  // 注意，这里因为设置了user表新增用户之后会给user对象返回id,以便接下来新增student表使用，所以：新增用户要在新增学生前面
            throw new RuntimeException("用户基本信息添加失败！");
        if(mapper.addStudentInfo(user.getId(), name, grade, sex) <= 0)
            throw new RuntimeException("学生信息添加失败");
    }

    @Override
    public AuthUser findUser(HttpSession session){
        AuthUser user = (AuthUser) session.getAttribute("user");
        if (user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            user = mapper.getPasswordByUsername(authentication.getName());
            session.setAttribute("user", user);
        }
        return user;
    }
}
