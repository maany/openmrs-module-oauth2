package org.openmrs.module.oauth2.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class MetadataController {

	/*
	 * OAuth module metadata endpoint.
	 * TODO Add support for automatic setting of Uris
	 */
	@RequestMapping(value = "/oauth/metadata", method = RequestMethod.GET)
	public ResponseEntity<HashMap<String,String>> getMetadata(){
		String fhirUri = "http://localhost:8080/openmrs/ws/fhir";
		String authUri = "http://localhost:8080/openmrs/ws/oauth/authorize";
		String tokenUri = "http://localhost:8080/openmrs/ws/oauth/token";
		HashMap<String,String> metadata = new HashMap<>();
		metadata.put("fhirUri",fhirUri);
		metadata.put("authUri",authUri);
		metadata.put("tokenUri",tokenUri);
		return new ResponseEntity<HashMap<String,String>>(metadata, HttpStatus.OK);
	}
}
