package com.projectTakeAway.Anima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.projectTakeAway.Anima.common.R;
import com.projectTakeAway.Anima.entity.ShoppingCart;
import com.projectTakeAway.Anima.mapper.ShoppingCartMapper;
import com.projectTakeAway.Anima.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@SuppressWarnings("all")

/**
 * @author CodemyMind
 * @Date 2022/5/24 22:43
 * Description：
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 删减购物车中的商品
     * @param shoppingCart
     */
    @Transactional
    public void subGoods(ShoppingCart shoppingCart) {

        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();

        // 代表数量减少的是菜品数量
        if (dishId != null) {
            //通过dishId查出购物车对象
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
            ShoppingCart cart1 = shoppingCartService.getOne(queryWrapper);
            cart1.setNumber(cart1.getNumber() - 1);
            //对数据进行更新操作
            shoppingCartService.updateById(cart1);
        }
        Long setmealId = shoppingCart.getSetmealId();
        if (setmealId != null) {
            // 代表是套餐数量减少
            queryWrapper.eq(ShoppingCart::getSetmealId, setmealId);
            ShoppingCart cart2 = shoppingCartService.getOne(queryWrapper);
            cart2.setNumber(cart2.getNumber() - 1);
            // 对数据进行更新操作
            shoppingCartService.updateById(cart2);
        }
    }
}
