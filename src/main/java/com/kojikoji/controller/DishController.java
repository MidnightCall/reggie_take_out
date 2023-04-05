package com.kojikoji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kojikoji.common.R;
import com.kojikoji.domain.Category;
import com.kojikoji.domain.Dish;
import com.kojikoji.domain.DishFlavor;
import com.kojikoji.dto.DishDto;
import com.kojikoji.service.CategoryService;
import com.kojikoji.service.DishFlavorService;
import com.kojikoji.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName DishController
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/3/27 15:03
 * @Version
 */

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        // 更新对应菜品分类之下的缓存数据
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name){
        //构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null,Dish::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行分页查询
        dishService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        log.info("接受到查询请求{}", id);

        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        // 清理对应菜品分类下的缓存数据
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
        // 更新数据库
        dishService.updateWithFlavor(dishDto);
        return R.success("更改成功");
    }

//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish){
//
//        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
//        lambdaQueryWrapper.eq(Dish::getStatus, 1);
//
//        List<Dish> list = dishService.list(lambdaQueryWrapper);
//
//        return R.success(list);
//    }

    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();

        // 首先查询redis
        List<DishDto> dishDtoList = null;
        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);

        // 若存在，则直接返回
        if(null != dishDtoList){
            return R.success(dishDtoList);
        }

        // 若不存在，则查询数据库

        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        lambdaQueryWrapper.eq(Dish::getStatus, 1);
        lambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(lambdaQueryWrapper);

        dishDtoList = list.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            // 由分类id查询分类名称
            LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Category::getId, categoryId);
            Category category = categoryService.getOne(lqw);
            if(category != null){
                dishDto.setCategoryName(category.getName());
            }

            // 查询口味数据
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> flavorQueryMapper = new LambdaQueryWrapper<>();
            flavorQueryMapper.eq(DishFlavor::getDishId, dishId);
            List<DishFlavor> flavorList = dishFlavorService.list(flavorQueryMapper);

            dishDto.setFlavors(flavorList);

            return dishDto;
        }).collect(Collectors.toList());

        // 更新redis数据库
        redisTemplate.opsForValue().set(key, dishDtoList, 5, TimeUnit.MINUTES);

        return R.success(dishDtoList);
    }
}
