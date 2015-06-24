package org.openmrs.module.oauth2.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.ClientRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

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

    @ModelAttribute("clients")
    public List<Client> getRegisteredClients() {
        List<Client> clients = null;
        ClientRegistrationService service = Context.getService(ClientRegistrationService.class);
        User user = Context.getAuthenticatedUser();
        clients = service.getAllClientsForClientDeveloper(user);
        return clients;
    }
}
