package com.kojikoji.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kojikoji.common.CustomerException;
import com.kojikoji.dao.CategoryDao;
import com.kojikoji.dao.EmployeeDao;
import com.kojikoji.domain.Category;
import com.kojikoji.domain.Dish;
import com.kojikoji.domain.Employee;
import com.kojikoji.domain.Setmeal;
import com.kojikoji.service.CategoryService;
import com.kojikoji.service.DishService;
import com.kojikoji.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName CategoryServiceImpl
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/3/27 9:38
 * @Version
 */

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id){
        // 查询是否关联了菜品
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if(count1 != 0){
            throw new CustomerException("分类关联了菜品");
        }

        // 查询是否关联了套餐
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if(count2 != 0){
            throw new CustomerException("分类关联了套餐");
        }

        super.removeById(id);
    }
}
