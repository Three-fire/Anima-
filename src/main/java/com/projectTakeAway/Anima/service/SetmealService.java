package com.projectTakeAway.Anima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.projectTakeAway.Anima.DataTransferObject.SetmealDto;
import com.projectTakeAway.Anima.entity.Setmeal;

import java.util.List;

@SuppressWarnings("all")
/**
 * @author CodemyMind
 * @Date 2022/5/17 10:22 AM
 * Description：
 */

public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐，同时需要保存套餐的和菜品的关联
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时删除套餐和菜品的关联数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids);

    /**
     * 根据ids修改套餐售卖的状态（含批量）
     * @param status
     * @param ids
     */
    public void alertSetmealStatus(Integer status, List<Long> ids);
}
