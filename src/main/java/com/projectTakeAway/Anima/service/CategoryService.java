package com.projectTakeAway.Anima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.projectTakeAway.Anima.entity.Category;

/**
 * @author CodemyMind
 * @Date 2022/5/17 9:24 AM
 * Description：
 */
public interface CategoryService extends IService<Category> {

    public void remove(Long id);
}
