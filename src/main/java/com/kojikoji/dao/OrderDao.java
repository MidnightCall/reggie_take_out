package com.kojikoji.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kojikoji.domain.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao extends BaseMapper<Orders> {
}
