package com.projectTakeAway.Anima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.projectTakeAway.Anima.entity.Employee;
import com.projectTakeAway.Anima.mapper.EmployeeMapper;
import com.projectTakeAway.Anima.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author 三火
 * @Date 2022/5/12 11:28 PM
 * Description：
 */

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
