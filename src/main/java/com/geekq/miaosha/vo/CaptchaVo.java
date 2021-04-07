package com.geekq.miaosha.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaVo {
    private String token;
    private String captchaImageBase64;
}
