package com.kojikoji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kojikoji.common.R;
import com.kojikoji.domain.User;
import com.kojikoji.service.UserService;
import com.kojikoji.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody Map map, HttpSession session){
        log.info("接收到了信息{}", map);
        String phone = (String) map.get("phone");
        if(null != phone){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
//            session.setAttribute(phone, code);
            // 在session中保存验证码，设置有效时间为5分钟
            System.out.println("验证码为:" + code + "，5分钟内有效");
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
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
        // 从redis中获取验证码
        String redisCode = (String) redisTemplate.opsForValue().get(phone);
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
            // 登陆成功后，在redis中删除验证码
            redisTemplate.delete(phone);
            return R.success(user);
        }

        return R.error("登陆失败");
    }

}
