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
package org.openmrs.module.oauth2.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.oauth2.api.db.Oauth2DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * It is a default implementation of  {@link Oauth2DAO}.
 */
@Repository
public class HibernateOauth2DAO<T> implements Oauth2DAO<T> {
    protected final Log log = LogFactory.getLog(this.getClass());
    protected Class<T> mappedClass;
    @Autowired
    protected SessionFactory sessionFactory;

    /**
     * Marked private because you *must* provide the class at runtime when instantiating one of
     * these, using the next constructor
     */
    private HibernateOauth2DAO() {
    }

    /**
     * You must call this before using any of the data access methods, since it's not actually
     * possible to write them all with compile-time class information due to use of Generics.
     *
     * @param mappedClass
     */
    protected HibernateOauth2DAO(Class<T> mappedClass) {
        this.mappedClass = mappedClass;
    }

    /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public T saveOrUpdate(T instance) {
        sessionFactory.getCurrentSession().saveOrUpdate(instance);
        return instance;
    }

    @Override
    public T getById(Integer id) {
        T instance = null;
        instance = (T) sessionFactory.getCurrentSession().get(mappedClass, id);
        return instance;
    }

    @Override
    public List<T> getAll() {
        return (List<T>) sessionFactory.getCurrentSession().createCriteria(mappedClass).list();
    }

    @Override
    public T update(T instance) {
        sessionFactory.getCurrentSession().update(instance);
        return instance;
    }

    @Override
    public void delete(T instance) {
        sessionFactory.getCurrentSession().delete(instance);
    }

    @Override
    public T merge(T instance) {
        return (T) sessionFactory.getCurrentSession().merge(instance);
    }
}