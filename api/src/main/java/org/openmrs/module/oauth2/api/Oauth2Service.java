/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.oauth2.api;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.oauth2.api.model.AuthorizedGrantType;
import org.openmrs.module.oauth2.api.model.Scope;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This service exposes module's core functionality. It is a Spring managed bean which is configured in moduleApplicationContext.xml.
 * <p>
 * It can be accessed only via Context:<br>
 * <code>
 * Context.getService(Oauth2Service.class).someMethod();
 * </code>
 * 
 * @see org.openmrs.api.context.Context
 */
@Transactional
public interface Oauth2Service extends OpenmrsService {

    /**
     *
     * @return List of all supported ${@link Scope}
     */
    public List<Scope> getAllSupportedScopes();

    /**
     *
     * @return List of all supported ${@link AuthorizedGrantType}
     */
    public List<AuthorizedGrantType> getAllSupportedGrantTypes();

    /**
     * Saves all the types of ${@link Scope} and @{@link AuthorizedGrantType} supported by the OAuth2 module
     */
    public void initializeDatabase() throws Exception;
}