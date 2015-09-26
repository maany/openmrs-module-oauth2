package org.openmrs.module.oauth2.web.util;

import org.openmrs.module.oauth2.api.model.Parametrized;

import java.beans.PropertyEditorSupport;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Mayank on 9/25/2015.
 */
public class CollectionPropertyEditor extends PropertyEditorSupport {
    private Class classType;

    public CollectionPropertyEditor(Class classType) {
        this.classType = classType;
    }

    /**
     *
     * returning all values stored in the Collection as a CSV
     *
     * @return
     */
    @Override
    public String getAsText() {
        String returnValue = "";
        try {
            Collection collection = (Collection) getValue();
            for (Object element : collection)
                returnValue += ((Parametrized)element).getParameter() + " , ";
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return returnValue;
    }

    /**
     * receive CSV string and create a Collection
     *
     * @param text
     * @throws IllegalArgumentException
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] values = text.split(" , ");
        setValue(createCollection(values));
    }

    public <T extends Parametrized> Collection<T> createCollection(String[] values) {
        Collection<T> collection = new HashSet<T>();
        for (String value : values) {
            T instance = null;
            try {
                instance = (T) classType.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            instance.setParameter(value);
            collection.add(instance);
        }
        return collection;
    }
}
