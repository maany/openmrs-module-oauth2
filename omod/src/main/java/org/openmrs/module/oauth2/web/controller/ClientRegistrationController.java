package org.openmrs.module.oauth2.web.controller;

import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.ClientRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by OPSKMC on 6/17/15.
 */
@Controller
@RequestMapping("/module/oauth2/*")
public class ClientRegistrationController {
    @RequestMapping(value = "registration", method = RequestMethod.GET)
    public void clientRegistrationForm(ModelMap model) {
        Client client = new Client();
        model.addAttribute("client", client);
    }

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public void submitClientRegistration(@ModelAttribute("client") Client client, Model model) {
        ClientRegistrationService clientRegistrationService = Context.getService(ClientRegistrationService.class);
        clientRegistrationService.registerNewClient(client);
        clientRegistrationService.registerNewClient(client);
        //TODO add success element and validation errors
//        model.addAttribute("result","");
    }

    @RequestMapping(value = "application", method = RequestMethod.GET)
    public void listRegisteredApplications(ModelMap model) {
        User clientDeveloper = Context.getAuthenticatedUser();
        model.addAttribute("user", Context.getAuthenticatedUser());
        ClientRegistrationService clientRegistrationService = Context.getService(ClientRegistrationService.class);
        List<Client> registeredApplications = clientRegistrationService.getAllClientsForClientDeveloper(clientDeveloper);
        model.addAttribute("registeredApplications", registeredApplications);
    }

    @RequestMapping(value = "authorized", method = RequestMethod.GET)
    public void listAuthorizedApplications(ModelMap model) {
        //TODO implement after adding at least the authorization code grant type
    }


}
