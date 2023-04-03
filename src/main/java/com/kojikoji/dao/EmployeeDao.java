package com.kojikoji.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kojikoji.domain.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName EmployeeMapper
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/3/25 15:07
 * @Version
 */

@Mapper
public interface EmployeeDao extends BaseMapper<Employee> {
}
