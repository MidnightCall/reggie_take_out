package com.kojikoji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kojikoji.dao.ShoppingCartDao;
import com.kojikoji.domain.ShoppingCart;
import com.kojikoji.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @ClassName ShoppingCartServiceImpl
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/4/1 14:26
 * @Version
 */

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartDao, ShoppingCart> implements ShoppingCartService {
}
