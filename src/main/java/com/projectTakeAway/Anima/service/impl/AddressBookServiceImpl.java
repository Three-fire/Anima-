package com.projectTakeAway.Anima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.projectTakeAway.Anima.entity.AddressBook;
import com.projectTakeAway.Anima.mapper.AddressBookMapper;
import com.projectTakeAway.Anima.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @author CodemyMind
 * @Date 2022/5/23 22:29
 * Description：
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
