package com.sptwin.apchy.web.controller;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("captcha")
public class CaptchaController {
    Logger logger = LoggerFactory.getLogger(CaptchaController.class);
    @Autowired
    private GenericManageableCaptchaService captchaService;

    @RequestMapping("/index")
    public void index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){

        OutputStream out = null;
        try {
            String captchaId = request.getSession().getId(); //获取会话id
            BufferedImage challenge = (BufferedImage) captchaService.getChallengeForID(captchaId,
                    request.getLocale()); //获取验证码图片缓冲
            //输出
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            out = response.getOutputStream();
            //编码JPG图片
            ImageIO.write(challenge, "JPG", out);
            //刷新
            out.flush();
        } catch (CaptchaServiceException ex) {
            logger.error( "生成验证码时发生异常", ex);
        } catch (IOException ex) {
            logger.error("输出验证码时发生异常", ex);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
}
