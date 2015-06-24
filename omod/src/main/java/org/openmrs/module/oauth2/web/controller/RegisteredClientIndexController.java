package org.openmrs.module.oauth2.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by OPSKMC on 6/24/15.
 */
@Controller
@RequestMapping(value = "module/oauth2/registeredClient/index.htm")
public class RegisteredClientIndexController {

    protected final Log log = LogFactory.getLog(getClass());
    private static final String SUCCESS_VIEW = "/module/oauth2/index";

    @RequestMapping(method = RequestMethod.GET)
    public String showList() {
        log.debug("Inside showList controller for RegisteredClient index.jsp");
        return SUCCESS_VIEW;
    }
}
