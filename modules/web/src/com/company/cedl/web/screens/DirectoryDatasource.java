package com.company.cedl.web.screens;

import com.company.cedl.entity.Directory;
import com.company.cedl.service.DirectoryService;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.data.impl.CustomHierarchicalDatasource;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class DirectoryDatasource extends CustomHierarchicalDatasource<Directory, UUID> {

    private DirectoryService directoryService = AppBeans.get(DirectoryService.NAME);

    @Override
    protected Collection<Directory> getEntities(Map params) {

        String startPath = (String) params.get("startPath");
        if (startPath != null) {
            return directoryService.listDirectories(startPath);
        }
        else {
            return new HashSet<Directory>();
        }


    }
}