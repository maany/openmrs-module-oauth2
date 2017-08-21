package org.openmrs.module.oauth2.web.controller;

import org.apache.commons.lang.StringUtils;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.ClientRegistrationService;
import org.openmrs.module.oauth2.api.JsonMappableClient;
import org.openmrs.module.oauth2.api.db.hibernate.ClientDAO;
import org.openmrs.module.oauth2.api.model.AuthorizedGrantType;
import org.openmrs.module.oauth2.api.model.RedirectURI;
import org.openmrs.module.oauth2.api.model.Scope;
import org.openmrs.module.oauth2.api.util.ClientSpringOAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Sanatt on 23-07-2017.
 */
@RestController
public class ClientManagementController {

	@Autowired
	private ClientRegistrationService clientRegistrationService;

	@Autowired
	private ClientDAO dao;

	/*
	 *See all registered clients for a particular OpenMRS user
	 */
	@RequestMapping(value = "/oauth/clientManagement", method = RequestMethod.GET,
	params = {"username", "password"})
	public ResponseEntity<List<JsonMappableClient>> listAllUsers(String username, String password) {
		User openmrsUser = Context.getUserService().getUserByUsername(username);
		List<Client> clients = dao.getAllClientsForClientDeveloper(openmrsUser);
		if(clients.isEmpty()){
			return new ResponseEntity<List<JsonMappableClient>>(HttpStatus.NO_CONTENT);
		}
		List<JsonMappableClient> jsonMappableClients = new ArrayList<>();
		for(Client c : clients){
			jsonMappableClients.add(new JsonMappableClient(c));
		}

		return new ResponseEntity<List<JsonMappableClient>>(jsonMappableClients, HttpStatus.OK);
	}

	/*
	* Register a new client
	 */
	@RequestMapping(value = "/oauth/clientManagement", method = RequestMethod.POST,
	params = {"username", "password", "name", "redirectionUri", "clientType", "scopes", "grantTypes"})
	public ResponseEntity<JsonMappableClient> createNewUser(String username, String password, String name, String description,
																  String website, String redirectionUri, String clientType,
																  String[] scopes, String[] grantTypes){

		Client client = getNewClient();
		client.setName(name);
		if(description != null) client.setDescription(description);
		if(website != null) client.setWebsite(website);

		Collection<RedirectURI> redirectURICollection= ClientSpringOAuthUtils.commaDelimitedStringToCollection(redirectionUri.trim(),client,RedirectURI.class);
		client.setClientType(Client.ClientType.valueOf(clientType));

		String scopesCSV = StringUtils.join(scopes, ',');
		Collection<Scope> scopeCollection = ClientSpringOAuthUtils.commaDelimitedStringToCollection(scopesCSV, client, Scope.class);
		client.setScopeCollection(scopeCollection);

		String grantTypeCSV = StringUtils.join(grantTypes,",");
		Collection<AuthorizedGrantType> grantTypeCollection = ClientSpringOAuthUtils.commaDelimitedStringToCollection(grantTypeCSV,client,AuthorizedGrantType.class);
		client.setGrantTypeCollection(grantTypeCollection);

		client.setCreator(Context.getUserService().getUserByUsername(username));
		client.setAccessTokenValiditySeconds(600);
		client.setRefreshTokenValiditySeconds(600);

		client = getService().saveOrUpdateClient(client);
		getService().generateAndPersistClientCredentials(client);
		getService().saveOrUpdateClient(client);

		JsonMappableClient jsonMappableClient = new JsonMappableClient(client);

		return new ResponseEntity<JsonMappableClient>(jsonMappableClient, HttpStatus.CREATED);
	}

	/*
	* Delete or Unregister an oauth client
	* @param client_id The client identifier
	* @param client_secret The client secret
	 */
	@RequestMapping(value = "/oauth/clientManagement", method = RequestMethod.DELETE,
		params = {"client_id","client_secret"})
	public ResponseEntity<String> deleteUser(String client_id, String client_secret){
		//verify the credentials
		Client tempClient = new Client(client_id, client_secret, null, null, null, null, null);
		List<String> encodedCredentials = clientRegistrationService.encodeCredentials(tempClient);
		Client client = (Client) dao.loadClientByClientId(client_id);
		if (!clientRegistrationService.verifyClientCredentials(client, encodedCredentials.get(0), encodedCredentials.get(1)))
			return new ResponseEntity<String>("Bad credentials", HttpStatus.UNAUTHORIZED);
		getService().unregisterClient(client);
		return new ResponseEntity<String>("Client deleted", HttpStatus.OK);
	}

	/*
	* Delete or Unregister an oauth client
	* @param client_id The client identifier
	* @param client_secret The client secret
	 */
	@RequestMapping(value = "/oauth/clientManagement", method = RequestMethod.DELETE,
			params = {"client_id","username", "password"})
	public ResponseEntity<String> deleteUser(String client_id, String username, String password){
		//verify the OpenMRS user credentials
		Client client = (Client) dao.loadClientByClientId(client_id);
		User clientDeveloper = client.getCreator();
		User openmrsUser = Context.getUserService().getUserByUsername(username);
		if (clientDeveloper != openmrsUser)
			return new ResponseEntity<String>("Invalid client developer", HttpStatus.UNAUTHORIZED);
		getService().unregisterClient(client);
		return new ResponseEntity<String>("Client deleted", HttpStatus.OK);
	}



	public Client getNewClient() {
		Client client = new Client(null, null, null, null, null, null, null);
		return client;
	}

	public ClientRegistrationService getService() {
		return Context.getService(ClientRegistrationService.class);
	}
}
