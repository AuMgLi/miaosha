package com.geekq.miaosha.service;

import com.geekq.miaosha.domain.Captcha;

public interface CaptchaService {
    Captcha createCaptcha();
    boolean verifyCaptcha(String token, String inputCode);
}
