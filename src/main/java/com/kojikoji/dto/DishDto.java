package com.kojikoji.dto;

import com.kojikoji.domain.Dish;
import com.kojikoji.domain.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DishDto
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/3/27 15:39
 * @Version
 */

@Data
public class DishDto extends Dish {
    private List<DishFlavor> flavors = new ArrayList<>();
    private String categoryName;
    private Integer copies;
}
