package com.projectTakeAway.Anima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.projectTakeAway.Anima.entity.Orders;
@SuppressWarnings("all")

/**
 * @author CodemyMind
 * @Date 2022/5/26 22:32
 * Description：
 */
public interface OrdersService extends IService<Orders> {

    // 用户下单
    public void submit(Orders orders);
}
