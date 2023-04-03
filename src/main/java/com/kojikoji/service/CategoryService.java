package com.kojikoji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kojikoji.domain.Category;

public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
