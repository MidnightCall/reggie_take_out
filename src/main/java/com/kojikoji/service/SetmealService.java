package com.kojikoji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kojikoji.domain.Setmeal;
import com.kojikoji.dto.SetmealDto;

import javax.jnlp.BasicService;
import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveSetmealWithDish(SetmealDto setmealDto);

    public void removeWithDish(List<Long> ids);
}
