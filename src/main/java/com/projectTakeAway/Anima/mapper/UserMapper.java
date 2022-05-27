package com.projectTakeAway.Anima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.projectTakeAway.Anima.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author CodemyMind
 * @Date 2022/5/22 4:10 PM
 * Description：
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
