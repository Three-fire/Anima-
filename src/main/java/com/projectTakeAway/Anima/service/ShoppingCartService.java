package com.projectTakeAway.Anima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.projectTakeAway.Anima.entity.ShoppingCart;

@SuppressWarnings("all")

/**
 * @author CodemyMind
 * @Date 2022/5/24 22:42
 * Description：
 */
public interface ShoppingCartService extends IService<ShoppingCart> {

    // 删减购物车中的商品
    public void subGoods(ShoppingCart shoppingCart);
}
