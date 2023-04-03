package com.kojikoji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kojikoji.common.R;
import com.kojikoji.domain.Category;
import com.kojikoji.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.LambdaMetafactory;
import java.util.List;

/**
 * @ClassName CategoryController
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/3/27 9:41
 * @Version
 */

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("接收到保存对象{}", category.toString());

        categoryService.save(category);

        return R.success("新增分类成功");
    }

    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize){
        log.info("接收到分页请求，页号{}，页面大小{}", page, pageSize);

        Page<Category> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.orderByAsc(Category::getSort);

        categoryService.page(pageInfo, lqw);

        return R.success(pageInfo);
    }

    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("接收到删除请求{}", ids);

        categoryService.remove(ids);

        return R.success("分类信息删除成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("接收到了更新请求{}", category);

        categoryService.updateById(category);

        return R.success("分类更新成功");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        log.info("接收到分类查询请求,查询分类id{}", category);
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.eq(category.getType() != null, Category::getType, category.getType());
        lqw.orderByAsc(Category::getSort);
        List<Category> list = categoryService.list(lqw);
        return R.success(list);
    }
}
