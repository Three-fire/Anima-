package com.projectTakeAway.Anima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.projectTakeAway.Anima.entity.ShoppingCart;
import com.projectTakeAway.Anima.mapper.ShoppingCartMapper;
import com.projectTakeAway.Anima.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author CodemyMind
 * @Date 2022/5/24 22:43
 * Description：
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
