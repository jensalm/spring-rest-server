package com.captechconsulting.core.service;

import com.captechconsulting.core.dao.UserDao;
import com.captechconsulting.core.domain.User;
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

    public User store(User user) {

        if (user.getId() != null) {
            return userDao.update(user);
        } else {
            return userDao.create(user);
        }
    }
}
