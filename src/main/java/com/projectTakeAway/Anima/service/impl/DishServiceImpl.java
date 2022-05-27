package com.projectTakeAway.Anima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.projectTakeAway.Anima.DataTransferObject.DishDto;
import com.projectTakeAway.Anima.common.CustomException;
import com.projectTakeAway.Anima.entity.Dish;
import com.projectTakeAway.Anima.entity.DishFlavor;
import com.projectTakeAway.Anima.mapper.DishMapper;
import com.projectTakeAway.Anima.service.DishFlavorService;
import com.projectTakeAway.Anima.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("all")

/**
 * @author CodemyMind
 * @Date 2022/5/17 10:23 AM
 * Description：
 */

@Service
@Slf4j
@Transactional
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;


    /**
     * 新增菜品，同时保存对应的口味数据
     * 两张表，需要事物控制
     * @param dishDto
     */
    public void saveWithFlavor(DishDto dishDto) {
        // 保存菜品的基本信息到dish表
        this.save(dishDto);

        Long dishId = dishDto.getId();

        // 菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        // 将dishId赋值给相对应的dish_flavors中的DishId
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        // 保存菜品口味数据到dish_flavor表
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id) {
        // 先查询菜品基本信息，从dish表中查询
        Dish dish = this.getById(id);

        // 对象拷贝
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        // 再查询当前菜品对应的口味信息，从dish_flavor表中查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    /**
     * 更新菜品信息，同时更新对应的口味信息
     * @param dishDto
     */
    public void updateWithFlavor(DishDto dishDto) {
        // 更新dish表基本信息
        this.updateById(dishDto);

        // 清理当前菜品对应口味数据 --> dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());

        dishFlavorService.remove(queryWrapper);

        // 再提交当前过来的口味数据 --> dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();
        // 将dishId赋值给相对应的dish_flavors中的DishId
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据套餐id启售或停售菜品（含批量）
     * @param status
     * @param ids
     */
    public void alertDishStatus(Integer status, List<Long> ids) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ids != null, Dish::getId, ids);
        // 根据ids查到相应的菜品
        List<Dish> list = dishService.list(queryWrapper);
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
            return dishService.updateById(item);
        }).collect(Collectors.toList());

    }

    /**
     * 根据ids删除菜品，同时删除菜品和口味的关联数据
     * @param ids
     */
    public void removeWithFlavor(List<Long> ids) {
        // 查询菜品的状态，确定是否可以删除 ==> select count(*) from dish where id in (1,2...) and status = 1
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId, ids);
        queryWrapper.eq(Dish::getStatus, 1);

        int count = this.count(queryWrapper);
        // 如果不能删除，抛出一个业务异常
        if (count > 0) {
            throw new CustomException("菜品正在售卖中，不能删除");
        }

        // 如果可以删除，先删除dish表中的数据
        this.removeByIds(ids);


        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // delete from dish_flavor where setmeal_id in (1,2...)
        lambdaQueryWrapper.in(DishFlavor::getDishId, ids);

        // 再删除dish_flavor表的数据
        dishFlavorService.remove(lambdaQueryWrapper);
    }





}
