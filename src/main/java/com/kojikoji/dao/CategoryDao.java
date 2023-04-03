package com.kojikoji.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kojikoji.domain.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName CategoryDAo
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/3/27 9:37
 * @Version
 */

@Mapper
public interface CategoryDao extends BaseMapper<Category> {
}
