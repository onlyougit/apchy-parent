package com.sptwin.apchy.web.controller;

import com.alibaba.fastjson.JSON;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;
import com.sptwin.apchy.web.service.SessionService;
import com.sptwin.apchy.web.sys.pojo.UserCustom;
import com.sptwin.apchy.web.sys.service.UserService;
import com.sptwin.spchy.model.common.ApplicationError;
import com.sptwin.spchy.model.common.ResponseJson;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @RequestMapping("/main")
    public String mainPage(){
        return "main";
    }

    @RequestMapping("/403")
    public String forbidden(){
        return "403";
    }

    @PostMapping(value = "/authentication")
    @ResponseBody
    public ResponseJson<Object> authentication(String json, HttpServletRequest request) {
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
        } catch (LockedAccountException lae) {
            usernamePasswordToken.clear();
            responseJson.setCode(ApplicationError.USERNAME_LOCKED.getCode());
            responseJson.setMsg(ApplicationError.USERNAME_LOCKED.getMessage());
            return responseJson;
        } catch (AuthenticationException e) {
            usernamePasswordToken.clear();
            responseJson.setCode(ApplicationError.USERNAME_PW_ERROR.getCode());
            responseJson.setMsg(ApplicationError.USERNAME_PW_ERROR.getMessage());
            return responseJson;
        }
        return responseJson;
    }
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }
    @RequestMapping("/changePassword")
    @ResponseBody
    public ResponseJson<Object> changePassword(String json){
        ResponseJson<Object> responseJson = new ResponseJson<>();
        Long userId = sessionService.getUserId();
        UserCustom userCustom = JSON.parseObject(json, UserCustom.class);
        if(null == userCustom){
            responseJson.setCode(ApplicationError.PW_UPDATE_FAIL.getCode());
            responseJson.setMsg(ApplicationError.PW_UPDATE_FAIL.getMessage());
            return responseJson;
        }
        if(!userCustom.getUserNewPw().equalsIgnoreCase(userCustom.getUserNewPwConfirm())){
            responseJson.setCode(ApplicationError.TWO_PASSWORD_ERROR.getCode());
            responseJson.setMsg(ApplicationError.TWO_PASSWORD_ERROR.getMessage());
            return responseJson;
        }
        userCustom.setId(userId);
        responseJson = userService.changePassword(userCustom);
        return responseJson;
    }
    public boolean checkAuthCode(UserCustom userCustom, HttpServletRequest request){
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
