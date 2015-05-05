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
package org.openmrs.module.oauth2.api.db;

import org.openmrs.module.oauth2.api.Oauth2Service;

import java.util.List;

/**
 *  Database methods for {@link Oauth2Service}.
 */
public interface Oauth2DAO<T> {
	
	/*
	 * Add DAO methods here
	 */

    /**
     * Save or Update details
     * @param instance
     */
    public void saveOrUpdate(T instance);
    /**
     *
     * @param id
     * @return
     */
    public T getById(Integer id);

    /**
     *
     * @return
     */
    public List<T> getAll();

    /**
     *
     * @param id
     */
    public void update(T instance);

    /**
     *
     * @param id
     */
    public void delete(T instance);


}