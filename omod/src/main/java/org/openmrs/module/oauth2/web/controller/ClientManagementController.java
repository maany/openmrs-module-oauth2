package org.openmrs.module.oauth2.web.controller;

import com.google.code.gson.*;
import org.codehaus.jackson.map.util.JSONPObject;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.ClientRegistrationService;
import org.openmrs.module.oauth2.api.db.hibernate.ClientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
	@RequestMapping(value = "/oauth/clientManagement", method = RequestMethod.GET)
	public ResponseEntity<String> listAllClients(){
		User openmrsUser = Context.getUserService().getUser(1);
		List<Client> clients = dao.getAllClientsForClientDeveloper(openmrsUser);
		Gson gson = new Gson();
		String response = "";
		response = gson.toJson(clients);
		System.out.println(response);
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}
}
