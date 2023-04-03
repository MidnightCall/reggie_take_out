package com.kojikoji.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kojikoji.domain.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName AddressBookDao
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/4/1 10:25
 * @Version
 */

@Mapper
public interface AddressBookDao extends BaseMapper<AddressBook> {
}
