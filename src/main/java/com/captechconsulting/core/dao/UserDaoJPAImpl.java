package com.captechconsulting.core.dao;

import com.captechconsulting.core.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UserDaoJPAImpl extends AbstractJPADao<User> implements UserDao {

    public UserDaoJPAImpl() {
        super(User.class);
    }
}
