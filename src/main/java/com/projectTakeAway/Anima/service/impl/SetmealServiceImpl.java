package com.projectTakeAway.Anima.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.projectTakeAway.Anima.DataTransferObject.SetmealDto;
import com.projectTakeAway.Anima.common.CustomException;
import com.projectTakeAway.Anima.entity.Setmeal;
import com.projectTakeAway.Anima.entity.SetmealDish;
import com.projectTakeAway.Anima.mapper.SetmealMapper;
import com.projectTakeAway.Anima.service.SetmealDishService;
import com.projectTakeAway.Anima.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("all")

/**
 * @author CodemyMind
 * @Date 2022/5/17 10:25 AM
 * Description：
 */
@Service
@Slf4j
@Transactional
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐，同时需要保存套餐的和菜品的关联
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto) {
        // 保存套餐的基本信息，操作setmeal表，执行insert操作
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        // 保存套餐和菜品的关联信息，操作setmeal_dish表，执行insert操作
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 根据ids删除套餐，同时删除套餐和菜品的关联数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids) {
        // 查询套餐的状态，确定是否可以删除 ==> select count(*) from setmeal where id in (1,2...) and status = 1
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);

        int count = this.count(queryWrapper);
        // 如果不能删除，抛出一个业务异常
        if (count > 0) {
            throw new CustomException("套餐正在售卖中，不能删除");
        }

        // 如果可以删除，先删除setmeal表中的数据
        this.removeByIds(ids);


        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // delete from setmeal_dish where setmeal_id in (1,2...)
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);

        // 再删除setmeal_dish表的数据
        setmealDishService.remove(lambdaQueryWrapper);
    }

    /**
     * 根据ids修改套餐售卖的状态(含批量)
     * @param status
     * @param ids
     */
    public void alertSetmealStatus(Integer status, List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ids != null, Setmeal::getId, ids);
        // 根据ids查到相应的菜品
        List<Setmeal> list = setmealService.list(queryWrapper);
        // 循环遍历list改变其status
//        for (Dish dish : list) {
//            if (dish != null) {
//                dish.setStatus(status);
//                dishService.updateById(dish);
//            }
//        }

        // 高效stream
        list.stream().map((item) -> {
            item.setStatus(status);
            return setmealService.updateById(item);
        }).collect(Collectors.toList());
    }


}
