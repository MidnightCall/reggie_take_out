package com.kojikoji.dto;

import com.kojikoji.domain.Setmeal;
import com.kojikoji.domain.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
