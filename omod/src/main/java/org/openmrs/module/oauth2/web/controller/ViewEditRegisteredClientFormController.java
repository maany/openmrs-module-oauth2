package org.openmrs.module.oauth2.web.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.ClientRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by OPSKMC on 6/24/15.
 */
@Controller
@RequestMapping(value = "module/oauth2/client/registered/view")
public class ViewEditRegisteredClientFormController {
    private static final String VIEW_EDIT_FORM_VIEW = "/module/oauth2/viewEditRegisteredClient";

    @RequestMapping(value = "/{clientId}", method = RequestMethod.GET)
    public String showForm(@PathVariable Integer clientId, ModelMap map) {
        Client client = getService().getClient(clientId);
        map.addAttribute("client", client);
        return VIEW_EDIT_FORM_VIEW;
    }

    private ClientRegistrationService getService() {
        return Context.getService(ClientRegistrationService.class);
    }

    @ModelAttribute("clientTypes")
    public Map<String, String> getClientTypes() {
        Client.ClientType[] clientTypes = getService().getAllClientTypes();
        Map<String, String> clientTypeMap = new HashMap<String, String>();
        for (Client.ClientType clientType : clientTypes) {
            clientTypeMap.put(clientType.name(), clientType.name());
        }
        return clientTypeMap;
    }
}
