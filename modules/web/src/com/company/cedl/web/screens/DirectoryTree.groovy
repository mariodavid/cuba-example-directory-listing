package com.company.cedl.web.screens

import com.company.cedl.entity.Directory
import com.company.cedl.service.DirectoryService;
import com.haulmont.cuba.gui.components.AbstractWindow
import com.haulmont.cuba.gui.components.Component
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.Tree
import com.haulmont.cuba.gui.data.HierarchicalDatasource;

import javax.inject.Inject;

class DirectoryTree extends AbstractWindow {

    @Inject
    DirectoryService directoryService

    @Inject
    HierarchicalDatasource<Directory, UUID> directoriesDs

    @Inject
    Tree tree

    @Inject
    TextField startPath

    @Override
    void init(Map<String, Object> params) {
        startPath.addValueChangeListener(new Component.ValueChangeListener() {
            @Override
            void valueChanged(Component.ValueChangeEvent e) {
                directoriesDs.refresh([startPath: startPath.value])
            }
        })
    }

    void touch() {
        directoryService.touchFileInDirectory(tree.singleSelected as Directory)
    }
}