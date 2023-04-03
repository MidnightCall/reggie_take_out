package com.kojikoji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kojikoji.dao.OrderDetailDao;
import com.kojikoji.domain.OrderDetail;
import com.kojikoji.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @ClassName OrderDetailService
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/4/1 15:49
 * @Version
 */

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailDao, OrderDetail> implements OrderDetailService {

}
