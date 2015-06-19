package org.openmrs.module.oauth2.web.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.oauth2.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
    public String submitClientRegistration(@ModelAttribute("client") Client client, Model model) {
        //ClientRegistrationService clientRegistrationService = Context.getService(ClientRegistrationService.class);
        //clientRegistrationService.registerNewClient(client);
        //clientRegistrationService.registerNewClient(client);
        return "/module/oauth2/manage";
    }

    @RequestMapping(value = "manage", method = RequestMethod.GET)
    public void manage(ModelMap model) {
        model.addAttribute("user", Context.getAuthenticatedUser());
    }

    @RequestMapping(value = "test")
    public ModelAndView testController() {
        System.out.println("******Controller Invoked, conc: .form is not required for controllers not bound to a view");

        return new ModelAndView("module/oauth/manage.form");
    }
}
