package com.company.cedl.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.AbstractNotPersistentEntity;

@MetaClass(name = "cedl$Directory")
public class Directory extends AbstractNotPersistentEntity {
    private static final long serialVersionUID = -2636016042768426533L;

    @MetaProperty
    protected String name;

    @MetaProperty
    protected String path;

    @MetaProperty(mandatory = true)
    protected Directory parent;

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public Directory getParent() {
        return parent;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }


}