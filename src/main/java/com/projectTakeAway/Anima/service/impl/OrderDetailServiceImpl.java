package com.projectTakeAway.Anima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.projectTakeAway.Anima.entity.OrderDetail;
import com.projectTakeAway.Anima.mapper.OrderDetailMapper;
import com.projectTakeAway.Anima.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author CodemyMind
 * @Date 2022/5/26 22:33
 * Description：
 */

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
