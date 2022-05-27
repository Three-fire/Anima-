package com.projectTakeAway.Anima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.projectTakeAway.Anima.entity.User;
import com.projectTakeAway.Anima.mapper.UserMapper;
import com.projectTakeAway.Anima.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author CodemyMind
 * @Date 2022/5/22 4:12 PM
 * Description：
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User>  implements UserService {
}
