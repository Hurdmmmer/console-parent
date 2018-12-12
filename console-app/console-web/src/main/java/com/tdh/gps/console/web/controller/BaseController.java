package com.tdh.gps.console.web.controller;

import com.tdh.gps.console.web.form.UserForm;
import com.tdh.gps.console.web.interceptor.UsernamePasswordInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class BaseController {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(ModelAndView mv) {
        mv.setViewName("index");
        mv.addObject("username", UsernamePasswordInterceptor.context.get());
        return mv;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute("userInfo") UserForm userForm, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) throws IOException {
        ModelAndView mv = new ModelAndView();
        if (StringUtils.isEmpty(userForm.getUsername()) || StringUtils.isEmpty(userForm.getPassword())) {
            mv.setViewName("login");
            return mv.addObject("msg", "账号密码不能为空");
        }
        attr.addFlashAttribute("name", userForm.getUsername());
        redirectStrategy.sendRedirect(request, response, "index");
        return null;
    }

    @GetMapping("/404")
    public String error() {
        return "error-404";
    }
}
