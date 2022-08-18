package book.manager.config;

import book.manager.entity.AuthUser;
import book.manager.mapper.UserMapper;
import book.manager.service.impl.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    //继承WebSecurityConfigurerAdapter，之后会进行配置
    @Resource
    UserAuthService service;

    @Resource
    UserMapper mapper;

    @Resource
    PersistentTokenRepository repository;

    @Bean
    public PersistentTokenRepository jdbcRepository(@Autowired DataSource dataSource){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();  //使用基于JDBC的实现
        repository.setDataSource(dataSource);   //配置数据源
//        repository.setCreateTableOnStartup(true);   //启动时自动创建用于存储Token的表（建议第一次启动之后删除该行）
        return repository;
    }

    // b.使用自定义的登录页面
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()   //首先需要配置哪些请求会被拦截，哪些请求必须具有什么角色才能访问
                .antMatchers("/static/**", "/page/auth/**", "/api/auth/**").permitAll()    //静态资源，使用permitAll来运行任何人访问（注意一定要放在前面）
                .antMatchers("/page/user/**", "/api/user/**").hasRole("user")
                .antMatchers("/page/admin/**", "api/admin/**").hasRole("admin")
                .anyRequest().hasAnyRole("user", "admin")     //所有请求必须登陆并且是user角色才可以访问（不包含上面的静态资源）
                .and()
                .formLogin()       //配置Form表单登陆
                .loginPage("/page/auth/login")       //登陆页面地址（GET）
                .loginProcessingUrl("/api/auth/login")    //form表单提交地址（POST）
//                .defaultSuccessUrl("/index")    //登陆成功后跳转的页面，也可以通过Handler实现高度自定义
                .successHandler(this::onAuthenticationSuccess)    //登陆成功后跳转的页面，通过Handler实现高度自定义
                .and()
                .logout()
                .logoutUrl("/api/auth/logout")    //退出登陆的请求地址
                .logoutSuccessUrl("/page/auth/login")    //退出后重定向的地址
                .and()
                .csrf().disable()
                .rememberMe()   //开启记住我功能
                .rememberMeParameter("remember")  //登陆请求表单中需要携带的参数，如果携带，那么本次登陆会被记住
//                .tokenRepository(new InMemoryTokenRepositoryImpl());  //这里使用的是直接在内存中保存的TokenRepository实现
        //TokenRepository有很多种实现，InMemoryTokenRepositoryImpl直接基于Map实现的，缺点就是占内存、服务器重启后记住我功能将失效
        //后面我们还会讲解如何使用数据库来持久化保存Token信息
                .tokenRepository(repository)
                .tokenValiditySeconds(60 * 60 * 24 *7);

    }

    // a.使用默认的登录
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 1.直接无数据库认证
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  //这里使用SpringSecurity提供的BCryptPasswordEncoder
//        auth
//            .inMemoryAuthentication() //直接验证方式，之后会讲解使用数据库验证
//            .passwordEncoder(encoder) //密码加密器
//            .withUser("test")   //用户名
//            .password(encoder.encode("123456"))   //这里需要填写加密后的密码
//            .roles("user");   //用户的角色（之后讲解）

        // 2.使用数据库认证
        auth.userDetailsService(service)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    private void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        HttpSession session = httpServletRequest.getSession();
        AuthUser user = mapper.getPasswordByUsername(authentication.getName());
        session.setAttribute("user", user);  // 登录之后：将用户信息放进session中，方便之后的页面调用

        if (user.getRole().equals("user")) {
            httpServletResponse.sendRedirect("/mvc/page/user/index");
        } else {
            httpServletResponse.sendRedirect("/mvc/page/admin/index");
        }

    }

}
