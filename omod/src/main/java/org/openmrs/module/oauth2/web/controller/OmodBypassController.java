package org.openmrs.module.oauth2.web.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.ClientRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by OPSKMC on 8/18/15.
 */
@Controller
public class OmodBypassController {

    @RequestMapping(value = "/initClients")
    public
    @ResponseBody
    String initClients(ModelMap map) {
        String response = "Failed to add clients";
        Client client = new Client("my-trusted-client-with-secret", "somesecret", "openmrs", "read,write", "password,authorization_code,implicit,client_credentials,refresh_token", "ROLE_CLIENT", "http://anywhere?key=value");
        Client client2 = new Client("openmrs-client", "secret", "openmrs", "read,write", "password,authorization_code,implicit,client_credentials,refresh_token", "ROLE_CLIENT", "http://anywhere?key=value");
        try {
            ClientRegistrationService clientRegistrationService = Context.getService(ClientRegistrationService.class);
            clientRegistrationService.saveOrUpdateClient(client);
            clientRegistrationService.saveOrUpdateClient(client2);
            response = "Clients added successfully";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
