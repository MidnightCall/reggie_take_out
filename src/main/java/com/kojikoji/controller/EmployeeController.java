package com.kojikoji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kojikoji.common.R;
import com.kojikoji.dao.EmployeeDao;
import com.kojikoji.domain.Employee;
import com.kojikoji.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @ClassName EmployeeController
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/3/25 15:11
 * @Version
 */

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        // 1.对密码使用md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2.由用户名查询数据库
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(lqw);

        // 3.未查询到则返回失败结果
        if(emp == null){
            return R.error("用户不存在");
        }

        // 4.账号密码不匹配则返回失败结果
        if(!emp.getPassword().equals(password)){
            return R.error("密码错误");
        }

        // 5.账号禁用则返回禁用信息
        if(emp.getStatus() == 0){
            return R.error("账号已禁用");
        }

        // 6.登陆成功，返回登陆成功信息
        request.getSession().setAttribute("employee", emp.getId());

        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().invalidate();
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        log.info("新增员工，员工信息{}", employee.toString());
        // 设置初始密码，使用md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // 设置创建更新时间
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

        // 设置创建用户id
//        Long id = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(id);
//        employee.setUpdateUser(id);

        employeeService.save(employee);

        return R.success("新增员工成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info("page={}, pagesize={}, name={}", page, pageSize, name);

        // 封装分页数据
        Page pageInfo = new Page(page, pageSize);

        // 封装查询条件
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.like(!StringUtils.isEmpty(name), Employee::getName, name);

        // 增加排序条件
        lqw.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, lqw);

        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
        log.info("获得用户信息{}", employee.toString());

//        Long id = (long) request.getSession().getAttribute("employee");
//        employee.setUpdateUser(id);
//        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);

        long id = Thread.currentThread().getId();
        log.info("当前线程id{}", id);

        return employee.getStatus() == 0 ? R.success("已禁用") : R.success("已启用");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("获得id==>{}", id);
        Employee emp = employeeService.getById(id);
        return emp != null ? R.success(emp) : R.error("未查询到对应员工");
    }
}
