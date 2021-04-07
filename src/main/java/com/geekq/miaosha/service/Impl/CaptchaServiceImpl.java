package com.geekq.miaosha.service.Impl;

import com.geekq.miaosha.domain.Captcha;
import com.geekq.miaosha.redis.RedisService;
import com.geekq.miaosha.redis.key.CaptchaKey;
import com.geekq.miaosha.service.CaptchaService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Locale;
import java.util.UUID;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    private static final int EXPIRE_SECONDS = 180;

    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private RedisService redisService;

    @Override
    public Captcha createCaptcha() {
        String token = UUID.randomUUID().toString();
        String captchaText = defaultKaptcha.createText();
        BufferedImage captchaImage = defaultKaptcha.createImage(captchaText);

        Captcha captcha = new Captcha();
        captcha.setToken(token);
        captcha.setCaptchaText(captchaText);
        captcha.setCaptchaImage(captchaImage);

        // 存入Redis
        redisService.set(new CaptchaKey(EXPIRE_SECONDS), token, captchaText);

        return captcha;
    }

    @Override
    public boolean verifyCaptcha(String token, String inputText) {
        String realText = redisService.get(new CaptchaKey(), token, String.class);
        if (realText == null || !realText.equalsIgnoreCase(inputText)) {
            return false;
        }
        redisService.delete(new CaptchaKey(), token);
        return true;
    }


}
