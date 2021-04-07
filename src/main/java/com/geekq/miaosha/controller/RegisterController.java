package com.geekq.miaosha.controller;

import com.geekq.miaosha.common.resultbean.ResultGeekQ;
import com.geekq.miaosha.domain.Captcha;
import com.geekq.miaosha.service.CaptchaService;
import com.geekq.miaosha.service.MiaoShaUserService;
import com.geekq.miaosha.service.MiaoshaService;
import com.geekq.miaosha.vo.CaptchaVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import static com.geekq.miaosha.common.enums.ResultStatus.CODE_FAIL;
import static com.geekq.miaosha.common.enums.ResultStatus.REGISTER_FAIL;

@Controller
@RequestMapping("/register")
@Slf4j
public class RegisterController {

    @Autowired
    private MiaoShaUserService miaoShaUserService;
    @Autowired
    private MiaoshaService miaoshaService;
    @Autowired
    private CaptchaService captchaService;

    @RequestMapping("/do_register")
    public String registerIndex() {
        return "register";
    }

    /**
     * 注册网站
     * @param userName:
     * @param password:
     * @param salt:
     * @return result:
     */
    @RequestMapping("/register")
    @ResponseBody
    public ResultGeekQ<String> register(@RequestParam("username") String userName,
                                        @RequestParam("password") String password,
                                        @RequestParam("verifyCode") String captchaText,
                                        @RequestParam("token") String token,
                                        @RequestParam("salt") String salt,
                                        HttpServletResponse response) {
        ResultGeekQ<String> result = ResultGeekQ.build();
        // 校验验证码
        boolean checkCaptcha = captchaService.verifyCaptcha(token, captchaText);
        if(!checkCaptcha){
            result.withError(CODE_FAIL.getCode(), CODE_FAIL.getMessage());
            return result;
        }
        log.info("password: " + password);
        boolean registerInfo = miaoShaUserService.register(response, userName, password, salt);
        if(!registerInfo){
           result.withError(REGISTER_FAIL.getCode(), REGISTER_FAIL.getMessage());
           return result;
        }
        return result;
    }

    @RequestMapping(value = "/register_captcha", method = RequestMethod.GET)
    @ResponseBody
    public ResultGeekQ<CaptchaVo> getCaptcha(HttpServletResponse response, Model model) {
        ResultGeekQ<CaptchaVo> result = ResultGeekQ.build();
        Captcha captcha = captchaService.createCaptcha();
        log.info(captcha.toString());
        BufferedImage captchaImage = captcha.getCaptchaImage();
        Base64.Encoder base64Encoder = Base64.getEncoder();
        model.addAttribute("captchaImageBase64", captchaImage);  // for thymeleaf
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {  // response.getOutputStream()
            ImageIO.write(captchaImage, "JPEG", out);
            String captchaImageBase64 = base64Encoder.encodeToString(out.toByteArray());

            CaptchaVo captchaVo = new CaptchaVo();
            captchaVo.setToken(captcha.getToken());
            captchaVo.setCaptchaImageBase64(captchaImageBase64);
            result.setData(captchaVo);
        } catch (IOException e) {
            log.error("生成注册验证码错误: " + e);
            result.withError(REGISTER_FAIL.getCode(), REGISTER_FAIL.getMessage());
            return result;
        }
        return result;
    }
}
