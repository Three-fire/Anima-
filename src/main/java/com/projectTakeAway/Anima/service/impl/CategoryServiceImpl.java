package com.projectTakeAway.Anima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.projectTakeAway.Anima.common.CustomException;
import com.projectTakeAway.Anima.entity.Category;
import com.projectTakeAway.Anima.entity.Dish;
import com.projectTakeAway.Anima.entity.Setmeal;
import com.projectTakeAway.Anima.mapper.CategoryMapper;
import com.projectTakeAway.Anima.service.CategoryService;
import com.projectTakeAway.Anima.service.DishService;
import com.projectTakeAway.Anima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("all")

/**
 * @author CodemyMind
 * @Date 2022/5/17 9:26 AM
 * Description：
 */

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 重写remove()方法 ==> 根据id删除分类，删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);

        // 查询当前分类是否关联了菜品，若已关联，则抛出业务异常
        if (count1 > 0) {
            // 已有关联菜品，抛异常
            throw new CustomException("当前分类下关联了菜品，禁止删除");
        }
        // 查询当前分类是否关联了套餐，若已关联，则抛出业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0) {
            // 已有关联套餐，抛异常
            throw new CustomException("当前分类下关联了套餐，禁止删除");
        }

        // 正常删除分类
        super.removeById(id);
    }
}
