package org.openmrs.module.oauth2.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.ClientRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

/**
 * Created by OPSKMC on 8/20/15.
 */
public class ClientAuthenticationServiceImpl implements AuthenticationProvider {
    private static final Log log = LogFactory.getLog(ClientAuthenticationServiceImpl.class);
    @Autowired
    ClientDetailsServiceImpl clientDetailsService;
    ClientRegistrationService clientRegistrationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("*** Seeking Client Authentication");
        clientRegistrationService = getClientRegistrationService();
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Client tempClient = new Client(username, password, null, null, null, null, null);
        List<String> encodedCredentials = clientRegistrationService.encodeCredentials(tempClient);
        Client client = (Client) clientDetailsService.loadClientByClientId(username);
        if (clientRegistrationService.verifyClientCredentials(client, encodedCredentials.get(0), encodedCredentials.get(1))) {
            return new UsernamePasswordAuthenticationToken(username, password, client.getAuthorities());
        } else if (client.getAdditionalInformation().get("REQUEST_SOURCE").equals(clientDetailsService.REQUEST_SOURCE)) {
            log.info("ClientAuthentcationProvider invoked by : " + clientDetailsService.REQUEST_SOURCE);
            client.getAdditionalInformation().remove("REQUEST_SOURCE");
            return new UsernamePasswordAuthenticationToken(username, password, client.getAuthorities());
        } else {
            log.info("No Credentials found for Client : " + username);
            return null;
        }


    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private ClientRegistrationService getClientRegistrationService() {
        if (clientRegistrationService == null)
            clientRegistrationService = Context.getService(ClientRegistrationService.class);
        return clientRegistrationService;
    }
}
