package com.kojikoji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kojikoji.dao.EmployeeDao;
import com.kojikoji.domain.Employee;
import com.kojikoji.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @ClassName EmployeeServiceImpl
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/3/25 15:09
 * @Version
 */

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmployeeService {
}
