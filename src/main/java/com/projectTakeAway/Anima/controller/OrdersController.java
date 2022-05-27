package com.projectTakeAway.Anima.controller;

import com.projectTakeAway.Anima.common.R;
import com.projectTakeAway.Anima.entity.Orders;
import com.projectTakeAway.Anima.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("all")

/**
 * @author CodemyMind
 * @Date 2022/5/26 22:35
 * Description：
 */

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        log.info("订单数据:{}",orders);

        ordersService.submit(orders);

        return R.success("下单成功");
    }


}
