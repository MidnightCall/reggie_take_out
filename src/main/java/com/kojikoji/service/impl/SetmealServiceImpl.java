package com.kojikoji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kojikoji.common.CustomerException;
import com.kojikoji.dao.SetmealDao;
import com.kojikoji.domain.Dish;
import com.kojikoji.domain.Setmeal;
import com.kojikoji.domain.SetmealDish;
import com.kojikoji.dto.SetmealDto;
import com.kojikoji.service.CategoryService;
import com.kojikoji.service.DishService;
import com.kojikoji.service.SetmealDishService;
import com.kojikoji.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivilegedAction;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName SetmealServiceImpl
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/3/27 10:25
 * @Version
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealDao, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void saveSetmealWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public void removeWithDish(List<Long> ids) {
        // 查询是否可以删除
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.in(Setmeal::getId,ids);
        lqw.eq(Setmeal::getStatus, 1);
        int count = this.count(lqw);
        if(count > 0){
            throw new CustomerException("套餐未停售，不可删除");
        }
        // 可以删除，删除套餐数据
        this.removeByIds(ids);
        // 删除套餐数据对应的菜品的关联关系
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);

        setmealDishService.remove(lambdaQueryWrapper);
    }
}
