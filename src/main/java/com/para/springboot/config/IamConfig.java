package com.para.springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: YixinZhang
 * @Date: Created in 23:05 2020/2/15
 * @Description:
 */
@Configuration
@ConfigurationProperties(prefix = "iam")
@PropertySource("classpath:application.properties")
@Data
@Component
public class IamConfig {

    //http://47.96.187.200/profile/oauth2/accessToken?
    private String tokenUrl;

    //RywqJUEK6F
    private String clientId;

    //0833fa44-02f4-4542-b028-f2e29d00c949
    private String clientSecret;

    //http://127.0.0.1:8080/callback
    private String redirectUri;

    //http://47.96.187.200/profile/oauth2/profile?
    private String profileUrl;

    //http://47.96.187.200/profile/oauth2/authorize?
    private String authorizeUrl;

    //https://47.96.187.200/service/api/v1/oauth2/checkIamService?
    private String checkIamService;
}
