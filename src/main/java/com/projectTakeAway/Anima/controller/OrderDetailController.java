package com.projectTakeAway.Anima.controller;

import com.projectTakeAway.Anima.common.R;
import com.projectTakeAway.Anima.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;



//    public R<String> submit() {
//
//
//
//        return R.success("下单成功");
//    }
}
