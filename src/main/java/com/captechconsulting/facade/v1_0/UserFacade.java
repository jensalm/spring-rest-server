package com.captechconsulting.facade.v1_0;

import com.captechconsulting.domain.User;
import com.captechconsulting.facade.Versions;
import com.captechconsulting.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@Controller
@RequestMapping("/user")
public class UserFacade {

    private Logger log = LoggerFactory.getLogger(UserFacade.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET,
            produces = Versions.V1_0, consumes = Versions.V1_0)
    public
    @ResponseBody
    User getUser(@PathVariable long userId) {
        if (log.isDebugEnabled()) {
            log.debug("Reguest for user [" + userId + "]");
        }
        User user = userService.getUser(userId);
        if (log.isTraceEnabled()) {
            log.trace("Found user [" + user.toString() + "]");
        }
        return user;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.POST,
            produces = Versions.V1_0, consumes = Versions.V1_0)
    public
    @ResponseBody
    User store(@PathVariable long userId, @RequestBody User user) {
        if (log.isDebugEnabled()) {
            log.debug("Reguest for storing user [" + user + "]");
        }
        user.setId(userId);
        User persisted = userService.store(user);
        if (log.isTraceEnabled()) {
            log.trace("Stored user [" + persisted + "]");
        }
        return persisted;
    }

    @RequestMapping(method = RequestMethod.POST,
            produces = Versions.V1_0, consumes = Versions.V1_0)
    public
    @ResponseBody
    User store(@RequestBody User user) {
        if (log.isDebugEnabled()) {
            log.debug("Reguest for storing user [" + user + "]");
        }
        User persisted = userService.store(user);
        if (log.isTraceEnabled()) {
            log.trace("Stored user [" + persisted + "]");
        }
        return persisted;
    }


}
