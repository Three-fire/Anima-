package com.projectTakeAway.Anima.DataTransferObject;

import com.projectTakeAway.Anima.entity.Setmeal;
import com.projectTakeAway.Anima.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
