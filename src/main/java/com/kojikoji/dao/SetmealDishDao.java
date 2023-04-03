package com.kojikoji.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kojikoji.domain.Setmeal;
import com.kojikoji.domain.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName SetmealDishDao
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/3/30 19:05
 * @Version
 */

@Mapper
public interface SetmealDishDao extends BaseMapper<SetmealDish> {
}
