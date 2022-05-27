package com.projectTakeAway.Anima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.projectTakeAway.Anima.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 三火
 * @Date 2022/5/12 11:26 PM
 * Description：
 */

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
