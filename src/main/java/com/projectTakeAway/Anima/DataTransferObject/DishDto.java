package com.projectTakeAway.Anima.DataTransferObject;

import com.projectTakeAway.Anima.entity.Dish;
import com.projectTakeAway.Anima.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("all")

@Data
public class DishDto extends Dish {

    // 菜品对应的口味数据
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
