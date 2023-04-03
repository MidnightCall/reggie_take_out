package com.kojikoji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kojikoji.dao.UserDao;
import com.kojikoji.domain.User;
import com.kojikoji.service.UserService;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;

/**
 * @ClassName UserServiceImpl
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/3/31 10:53
 * @Version
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
}
