package com.os.ovs.appservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@Controller
public class UserRestServiceController implements UserRestAPI {

    private static final Logger log = LoggerFactory.getLogger(UserRestServiceController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UserRestServiceController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public List<User> getUserDetails(@ApiParam(value = "ID of User to return",required=true) @PathVariable("id") Long id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new List<User>(objectMapper.readValue("{  \"firstName\" : \"firstName\",  \"lastName\" : \"lastName\",  \"id\" : 0}", User.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new List<User>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return UserDao.getUserDetails();
    }
}