package com.projectTakeAway.Anima.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.projectTakeAway.Anima.common.R;
import com.projectTakeAway.Anima.entity.Orders;
import com.projectTakeAway.Anima.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 后台系统查询订单明细
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String number,String beginTime,String endTime){
        //分页构造器对象
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        //构造条件查询对象
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();

        //添加查询条件  动态sql  字符串使用StringUtils.isNotEmpty这个方法来判断
        //这里使用了范围查询的动态SQL
        queryWrapper.like(number!=null,Orders::getNumber,number)
                .gt(StringUtils.isNotEmpty(beginTime),Orders::getOrderTime,beginTime)
                .lt(StringUtils.isNotEmpty(endTime),Orders::getOrderTime,endTime);

        ordersService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 移动端用户订单分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page> page(int page, int pageSize) {

        // 分页构造器对象
        Page<Orders> pageInfo = new Page<>(page, pageSize);

        // 构造条件查询对象
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();

        // 添加排序条件，根据更新时间降序
        queryWrapper.orderByDesc(Orders::getOrderTime);
        ordersService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

}
