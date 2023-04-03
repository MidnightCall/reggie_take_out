package com.kojikoji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kojikoji.domain.Orders;
import org.springframework.core.annotation.Order;

/**
 * @ClassName OrderService
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/4/1 15:47
 * @Version
 */

public interface OrderService extends IService<Orders> {
    public void submit(Orders orders);
}
