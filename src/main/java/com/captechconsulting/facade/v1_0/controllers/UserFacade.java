package com.captechconsulting.facade.v1_0.controllers;

import com.captechconsulting.core.domain.User;
import com.captechconsulting.facade.Versions;
import com.captechconsulting.core.service.UserService;
import com.captechconsulting.facade.v1_0.data.UserVO;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Transactional
@Controller
@RequestMapping("/user")
public class UserFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacade.class);

    @Autowired
    private UserService userService;

    @Autowired
    private DozerBeanMapper mapper;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = Versions.V1_0, consumes = Versions.V1_0)
    public @ResponseBody UserVO getUser(@PathVariable long userId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Request for user [" + userId + "]");
        }
        User user = userService.getUser(userId);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Found user [" + user.toString() + "]");
        }
        return mapper.map(user, UserVO.class);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT, produces = Versions.V1_0, consumes = Versions.V1_0)
    public @ResponseBody UserVO update(@PathVariable long userId, @Valid @RequestBody UserVO user) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Request to update user [" + user + "]");
        }
        User previouslyPersisted = userService.getUser(userId);
        mapper.map(user, previouslyPersisted);
        User persisted = userService.store(previouslyPersisted);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Updated user [" + persisted + "]");
        }
        return mapper.map(persisted, UserVO.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = Versions.V1_0, consumes = Versions.V1_0)
    public @ResponseBody UserVO create(@Valid @RequestBody UserVO user) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Request to store user [" + user + "]");
        }
        User mappedUser = mapper.map(user, User.class);
        User persisted = userService.store(mappedUser);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Stored user [" + persisted + "]");
        }
        return mapper.map(persisted, UserVO.class);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE, produces = Versions.V1_0, consumes = Versions.V1_0)
    public @ResponseBody UserVO remove(@PathVariable long userId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Request to delete user id [" + userId + "]");
        }
        User user = userService.getUser(userId);
        userService.delete(user);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Deleted user [" + user + "]");
        }
        return null;
    }

}
