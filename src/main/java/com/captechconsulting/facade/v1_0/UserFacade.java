package com.captechconsulting.facade.v1_0;

import com.captechconsulting.domain.User;
import com.captechconsulting.facade.Versions;
import com.captechconsulting.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Transactional
@Controller
@RequestMapping("/user")
public class UserFacade {

    private Logger log = LoggerFactory.getLogger(UserFacade.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET,
            produces = Versions.ACCEPT_HEADER_V1_0, consumes = Versions.ACCEPT_HEADER_V1_0)
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


}
