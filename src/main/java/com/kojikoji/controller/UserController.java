package com.kojikoji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kojikoji.common.R;
import com.kojikoji.domain.User;
import com.kojikoji.service.UserService;
import com.kojikoji.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ClassName UserController
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/3/31 10:56
 * @Version
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> login(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        if(null != phone){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            session.setAttribute(phone, code);
            return R.success("手机验证码发送成功");
        }

        return R.error("短信发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        log.info(map.toString());
        // 获取手机号
        String phone = map.get("phone").toString();
        // 获取验证码(用session中的code替代)
//        String code = session.getAttribute(phone).toString();
        String code = "1234";
        // 获取正确验证码
//        String sessionCode = session.getAttribute(phone).toString();
        String sessionCode = "1234";
        // 比对验证码是否正确
        if(code != null && code.equals(sessionCode)){
            // 比对成功，判断当前用户是否为新用户，新用户则完成注册
            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(User::getPhone, phone);
            User user = userService.getOne(lqw);
            if(null == user){
                // 完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            return R.success(user);
        }

        return R.error("登陆失败");
    }

}
