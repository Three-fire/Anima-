package com.projectTakeAway.Anima.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.projectTakeAway.Anima.common.R;
import com.projectTakeAway.Anima.entity.User;
import com.projectTakeAway.Anima.service.UserService;
import com.projectTakeAway.Anima.utils.SMSUtils;
import com.projectTakeAway.Anima.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")

/**
 * @author CodemyMind
 * @Date 2022/5/22 4:14 PM
 * Description：
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 发送手机短信验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(HttpSession session, @RequestBody User user) {
        // 获取手机号
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {
            // 生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();

            log.info("code = {}", code);    //方便验证，直接去Console里偷看验证码

            // 调用阿里云短信服务API完成发送短信
            //SMSUtils.sendMessage("Anima外卖", "", phone, code);

            // 需要将生成的验证码保存到Session
            //session.setAttribute(phone, code);

            // 将生成的验证码缓存到Redis中，并且设置有效期为5mins
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

            return R.success("手机验证码短信发送成功");
        }
        return R.error("短信发送失败");
    }

    /**
     * 移动端用户登陆
     * @param session
     * @param map
     * @return
     */
    @PostMapping("/login")
    public R<User> login(HttpSession session, @RequestBody Map map) {

        log.info("用户登陆数据：{}",map.toString());

        // 获取手机号
        String phone = map.get("phone").toString();
        // 获取验证码
        String code = map.get("code").toString();
        // 从Session中获取保存的验证码
        //Object codeInSession = session.getAttribute(phone);

        // 从Redis中获取缓存的验证码
        Object codeInSession = redisTemplate.opsForValue().get(phone);
        // 进行验证码的比对
        if (codeInSession != null && codeInSession.equals(code)) {
            // 对比成功，则说明登陆成功
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);

            User user = userService.getOne(queryWrapper);
            if (user == null) {
                // 判断当前手机号对应的用户是否为新用户，若是新用户则自动完成注册
                user = new User();
                user.setPhone(phone);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());

            // 若用户登陆成功，删除Redis中缓存的验证码
            redisTemplate.delete(phone);

            return R.success(user);
        }

        return R.error("登陆失败");
    }
}
