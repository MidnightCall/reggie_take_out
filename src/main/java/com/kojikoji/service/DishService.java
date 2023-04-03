package com.kojikoji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kojikoji.domain.Dish;
import com.kojikoji.dto.DishDto;
import org.springframework.context.annotation.ImportSelector;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
