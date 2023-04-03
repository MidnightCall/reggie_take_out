package com.kojikoji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kojikoji.dao.AddressBookDao;
import com.kojikoji.domain.AddressBook;
import com.kojikoji.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @ClassName AddressBookServiceImpl
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/4/1 10:26
 * @Version
 */

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookDao, AddressBook> implements AddressBookService {
}
