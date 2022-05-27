package com.projectTakeAway.Anima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.projectTakeAway.Anima.DataTransferObject.DishDto;
import com.projectTakeAway.Anima.entity.Dish;

import java.util.List;

@SuppressWarnings("all")

/**
 * @author CodemyMind
 * @Date 2022/5/17 10:21 AM
 * Description：
 */

public interface DishService extends IService<Dish> {

    // 新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    // 根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);

    // 更新菜品信息，同时更新对应的口味信息
    public void updateWithFlavor(DishDto dishDto);

    // 启售或停售菜品（含批量）
    public void alertDishStatus(Integer status, List<Long> ids);

    // 根据ids删除菜品，同时删除菜品和口味的关联数据
    public void removeWithFlavor(List<Long> ids);
}
