package org.openmrs.module.oauth2.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.ClientRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * This controller bypasses the major changes required to OMOD layer due to the new Client Structure.
 * Remove this, once API works all right
 * Created by OPSKMC on 8/18/15.
 */
@Controller
public class OmodBypassController {
    private static final Log log = LogFactory.getLog(OmodBypassController.class);

    @RequestMapping(value = "/initClients")
    public
    @ResponseBody
    String initClients(ModelMap map) {
        ClientRegistrationService clientRegistrationService = Context.getService(ClientRegistrationService.class);
        //clientRegistrationService.unregisterClient(clientRegistrationService.getClient(2));
        //clientRegistrationService.unregisterClient(clientRegistrationService.getClient(3));
        String response = "Failed to add clients";
        Client client = new Client("my-trusted-client-with-secret", "somesecret", "openmrs", "read,write", "password,authorization_code,implicit,client_credentials,refresh_token", "ROLE_CLIENT", "http://anywhere?key=value");
        client.setName("Sample Application");
        client.setClientType(Client.ClientType.WEB_APPLICATION);
        client.setDescription("This is a sample client");
        client.setWebsite("http://www.samplesomewhere.com");
        client.getRegisteredRedirectUri().add("http://www.samplesomewhere.com?key=value");
        client.setCreator(Context.getAuthenticatedUser());
        Client client2 = new Client("openmrs-client", "secret", "openmrs", "read,write", "password,authorization_code,implicit,client_credentials,refresh_token", "ROLE_CLIENT", "http://anywhere?key=value");
        client2.setName("Demo Application");
        client2.setClientType(Client.ClientType.WEB_APPLICATION);
        client2.setDescription("This is a demo client");
        client2.setWebsite("http://www.demosomewhere.com");
        client.getRegisteredRedirectUri().add("http://www.demosomewhere.com?key=value");
        System.out.println("Authenticated User" + Context.getAuthenticatedUser());
        client2.setCreator(Context.getAuthenticatedUser());
        log.info("Authenticated User : " + Context.getAuthenticatedUser());
        try {
            clientRegistrationService.saveOrUpdateClient(client);
            clientRegistrationService.generateAndPersistClientCredentials(client);
            clientRegistrationService.saveOrUpdateClient(client2);
            clientRegistrationService.generateAndPersistClientCredentials(client2);
            response = "Clients added successfully";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "/client/{id}")
    public
    @ResponseBody
    String getClient(@PathVariable int id, ModelMap map) {
        ClientRegistrationService clientRegistrationService = Context.getService(ClientRegistrationService.class);
        Client client = clientRegistrationService.getClient(id);
        return client.toString();
    }

    public Client getSampleClient() {
        Client client = new Client("my-trusted-client-with-secret", "somesecret", "openmrs", "read,write", "authorization_code,refresh_token,implicit,client_credentials,password", "ROLE_CLIENT", "http://anywhere?key=value");
        client.setVoided(false);
        client.setDateCreated(new Date());
        client.setName("Demo Application");
        client.setClientType(Client.ClientType.WEB_APPLICATION);
        client.getRegisteredRedirectUri().add("http://redirectUrlDoesNotMatter");
        return client;
    }
}
