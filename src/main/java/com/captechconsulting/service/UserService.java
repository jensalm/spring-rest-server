package com.captechconsulting.service;

import com.captechconsulting.dao.UserDao;
import com.captechconsulting.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUser(final long id) {
        return userDao.findById(id);
    }

}
