package com.projectTakeAway.Anima.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.projectTakeAway.Anima.DataTransferObject.DishDto;
import com.projectTakeAway.Anima.common.R;
import com.projectTakeAway.Anima.entity.Category;
import com.projectTakeAway.Anima.entity.Dish;
import com.projectTakeAway.Anima.entity.DishFlavor;
import com.projectTakeAway.Anima.service.CategoryService;
import com.projectTakeAway.Anima.service.DishFlavorService;
import com.projectTakeAway.Anima.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@SuppressWarnings("all")

/**
 * @author CodemyMind
 * @Date 2022/5/20 2:37 PM
 * Description：
 */

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody  DishDto dishDto) {
        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);


        // 清理所有菜品的缓存数据
        //Set keys = redisTemplate.keys("dish_*");
        // 精确清理某个分类下面的菜品缓存数据
        String key = "dish_" + dishDto.getCategoryId()+ "_1";
        redisTemplate.delete(key);

        return R.success("新增菜品成功");
    }

    /**
     * 菜品信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {

        // 构造分页构造器对象
        Page<Dish> pageInfo = new Page<Dish>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        // 条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
        queryWrapper.like(name != null, Dish::getName, name);
        // 添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        // 执行分页查询
        dishService.page(pageInfo, queryWrapper);

        // 对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        // 手动处理Page中的records
        List<Dish> records = pageInfo.getRecords();
        // Stream大法,比foreach更加高效更加复杂
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            // 将其它属性值拷贝
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();  // 分类id
            // 根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            // 判断下是否为空
            if (category != null ) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {

        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);
    }

    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());

        dishService.updateWithFlavor(dishDto);

        // 清理所有菜品的缓存数据
        //Set keys = redisTemplate.keys("dish_*");
        // 精确清理某个分类下面的菜品缓存数据
        String key = "dish_" + dishDto.getCategoryId()+ "_1";
        redisTemplate.delete(key);

        return R.success("菜品修改成功");
    }

    /**
     * 根据条件查询菜品数据, 以及其口味信息
     * @param dish
     * @return
     */
//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish) {
//
//        // 构造查询条件
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
//        // 查询状态为1这些 菜品
//        queryWrapper.eq(Dish::getStatus, 1);
//
//        // 添加排序条件
//        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//
//        List<Dish> list = dishService.list(queryWrapper);
//
//        return R.success(list);
//    }
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        List<DishDto> dishDtoList = null;

        // 动态构造Key
        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();

        // 从Redis中获取缓存数据
        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);

        if (dishDtoList != null) {
            // 如果存在，直接返回，无需查询数据库
            return R.success(dishDtoList);
        }


        // 构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        // 查询状态为1这些 菜品
        queryWrapper.eq(Dish::getStatus, 1);

        // 添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            // 将其它属性值拷贝
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();  // 分类id
            // 根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            // 判断下是否为空
            if (category != null ) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            // 当前菜品的id
            Long dishId = item.getId();
            // 查询口味的集合
            LambdaQueryWrapper<DishFlavor> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(DishFlavor::getDishId, dishId);
            // SQL: select * from dish_flavor where dish_id = ?
            List<DishFlavor> dishFlavorList = dishFlavorService.list(queryWrapper2);

            dishDto.setFlavors(dishFlavorList);

            return dishDto;
        }).collect(Collectors.toList());


        // 如果不存在，需要查询数据库，将查询到的菜品数据缓存到Redis
        redisTemplate.opsForValue().set(key, dishDtoList, 60, TimeUnit.MINUTES);

        return R.success(dishDtoList);
    }

    /**
     * 更新菜品售卖状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable("status") Integer status,@RequestParam List<Long> ids) {
        log.info("status: {}, ids: {}", status, ids);

        dishService.alertDishStatus(status, ids);
        return R.success("售卖状态修改成功");
    }

    /**
     * 删除菜品以及该菜品相应的口味
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("ids: {}", ids);

        dishService.removeWithFlavor(ids);
        return R.success("删除菜品成功");
    }
}
