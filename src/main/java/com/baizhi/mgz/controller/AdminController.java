package com.baizhi.mgz.controller;

import com.baizhi.mgz.entity.Admin;
import com.baizhi.mgz.service.AdminService;
import com.baizhi.mgz.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @ResponseBody
    @RequestMapping("selectOne")
    public String selectOne(HttpSession session, String username, String password, String clientCode) {
        String serverCode = session.getAttribute("ServerCode").toString();
        if (serverCode.equals(clientCode)) {
            Admin admin = adminService.queryByName(username);
            if (admin == null) {
                return "用户名或密码错误";
            } else if (password.equals(admin.getPassword())) {
                session.setAttribute("admin", admin);
                return null;
            }
        }
        return "验证码错误";
    }
    @RequestMapping("/createImg")
    public void createImg(HttpServletResponse response, HttpSession session) throws IOException {
        CreateValidateCode vcode = new CreateValidateCode();
        String code = vcode.getCode(); // 随机验证码
        vcode.write(response.getOutputStream()); // 把图片输出client
        session.setAttribute("ServerCode", code);
    }
    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("admin");
        return "jsp/login";
    }
}
