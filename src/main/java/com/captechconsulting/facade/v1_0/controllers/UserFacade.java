package com.captechconsulting.facade.v1_0.controllers;

import com.captechconsulting.core.domain.User;
import com.captechconsulting.facade.Versions;
import com.captechconsulting.core.service.UserService;
import com.captechconsulting.facade.v1_0.data.UserVO;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
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

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = Versions.V1_0, consumes = Versions.V1_0)
    public @ResponseBody UserVO getUser(@PathVariable long userId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Request for user [" + userId + "]");
        }
        User user = userService.getUser(userId);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Found user [" + user.toString() + "]");
        }
        return mapToUserVO(user);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST, produces = Versions.V1_0, consumes = Versions.V1_0)
    public @ResponseBody UserVO update(@PathVariable long userId, @Valid @RequestBody UserVO user) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Request to store user [" + user + "]");
        }
        user.setId(userId);
        User mappedUser = mapper.map(user, User.class);
        User persisted = userService.store(mappedUser);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Stored user [" + persisted + "]");
        }
        return mapToUserVO(persisted);
    }

    @Secured("ROLE_ADMIN")
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
        return mapToUserVO(persisted);
    }

    private UserVO mapToUserVO(User persisted) {
        UserVO user = mapper.map(persisted, UserVO.class);
        user.setPassword(null);
        return user;
    }

}
