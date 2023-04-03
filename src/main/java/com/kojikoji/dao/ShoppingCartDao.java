package com.kojikoji.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kojikoji.domain.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName ShoppingCartDao
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/4/1 14:25
 * @Version
 */

@Mapper
public interface ShoppingCartDao extends BaseMapper<ShoppingCart> {
}
