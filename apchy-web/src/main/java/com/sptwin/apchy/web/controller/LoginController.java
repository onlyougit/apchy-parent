package com.sptwin.apchy.web.controller;

import com.alibaba.fastjson.JSON;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;
import com.sptwin.apchy.web.entity.User;
import com.sptwin.apchy.web.sys.pojo.UserCustom;
import com.sptwin.spchy.model.common.ApplicationError;
import com.sptwin.spchy.model.common.ResponseJson;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private GenericManageableCaptchaService captchaService;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @RequestMapping("/main")
    public String mainPage(){
        return "main";
    }

    @PostMapping(value = "/authentication")
    @ResponseBody
    public ResponseJson<Object> authentication(String json, HttpServletRequest request, Model model, HttpSession session) {
        ResponseJson<Object> responseJson = new ResponseJson<>();
        UserCustom userCustom = JSON.parseObject(json, UserCustom.class);
        if (null != userCustom) {
            boolean pass = checkAuthCode(userCustom, request);
            if (!pass) {
                responseJson.setCode(ApplicationError.AUTHCODE_ERROR.getCode());
                responseJson.setMsg(ApplicationError.AUTHCODE_ERROR.getMessage());
                return responseJson;
            }
        }else{
            responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
            responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
            return responseJson;
        }
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userCustom.getUserName(), userCustom.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);   //完成登录
            User user = (User) subject.getPrincipal();
            //更新用户登录时间，也可以在ShiroRealm里面做
            session.setAttribute("user", user);
        } catch (Exception e) {
            responseJson.setCode(ApplicationError.USERNAME_PW_ERROR.getCode());
            responseJson.setMsg(ApplicationError.USERNAME_PW_ERROR.getMessage());
            return responseJson;
        }
        return responseJson;
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session,Model model) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        model.addAttribute("msg","安全退出！");
        return "login";
    }
    protected boolean checkAuthCode(UserCustom userCustom, HttpServletRequest request){
        String code = userCustom.getAuthCode();
        if (StringUtils.isBlank(code)) {
            return false;
        }
        //检查验证码
        String captchaId = request.getSession().getId();
        try {
            return captchaService.validateResponseForID(captchaId, code);
        } catch (CaptchaServiceException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
