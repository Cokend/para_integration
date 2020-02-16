package com.para.springboot.controlle;

import com.para.springboot.config.IamConfig;
import com.para.springboot.utils.HttpRequestUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: YixinZhang
 * @Date: Created in 11:19 2020/1/8
 * @Description:
 */

@Controller
@EnableConfigurationProperties(IamConfig.class)
//@RequestMapping("/user")
public class LoginController {

    @Autowired
    IamConfig iamConfig;

    @GetMapping("/callback")
    @ResponseBody
    public String callback(HttpServletRequest request,
                           HttpServletResponse respons,
                           HttpSession session) throws  Exception{
        //心跳检测
        String iamServiceParam = HttpRequestUtil.getIAMServiceParam(iamConfig.getClientId(), iamConfig.getClientSecret());
        String flag = HttpRequestUtil.getResult(iamConfig.getCheckIamService(), iamServiceParam);

        //IAM心跳检测正常
        if("OK".equals(flag)){
            //得到iam返回的OAuth code
            String code = request.getParameter("code");

            //拿OAuth Code调用IAM系统API换取Access Token
            String tokenParam = HttpRequestUtil.getAccessTokenParam(iamConfig.getClientId(),iamConfig.getClientSecret(),
                    iamConfig.getRedirectUri(), code);
            String token = HttpRequestUtil.getResult(iamConfig.getTokenUrl(), tokenParam);

            //将字符串转换成json格式
            JSONObject tokenJson = JSONObject.fromObject(token);

            //拿Access Token调用IAM系统API换取用户信息
            String userParam = HttpRequestUtil.getUserParam(iamConfig.getClientId(),
                    iamConfig.getClientSecret(),
                    tokenJson.getString("access_token"));
            String user = HttpRequestUtil.getResult(iamConfig.getProfileUrl(), userParam);

            //将用户id放到Sesion中 用于登录拦截
            JSONObject userJson=JSONObject.fromObject(user);
            session.setAttribute("loginUser",userJson.getString("id"));

            return "用户信息："+user;
        }else {
            return "login";
        }


    }

    //@RequestMapping("/login")
    @PostMapping("/user/login")
    public String login(@RequestParam("username") String userNmae,
                        @RequestParam("password") String password,
                        HttpSession session){

        if(!StringUtils.isEmpty(userNmae) && "123".equals(password)){
            //防止表单重传，采用重定向的方式
            session.setAttribute("loginUser",userNmae);
            return "redirect:/main.html";
        }else{
            session.setAttribute("msg","登录失败！");
            return "redirect:/login.do";


        }
    }

    @RequestMapping("/user/signOut")
    public String singOut(HttpSession session){
        session.invalidate();
        return "login";
    }
}
