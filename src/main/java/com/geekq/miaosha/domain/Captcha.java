package com.geekq.miaosha.domain;

import lombok.*;

import java.awt.image.BufferedImage;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Captcha {
    private String token;
    private String captchaText;
    private BufferedImage captchaImage;
}
