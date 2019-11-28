package com.company.cedl.web.screens

import com.company.cedl.entity.Directory
import com.company.cedl.service.DirectoryService
import com.haulmont.cuba.gui.components.AbstractWindow
import com.haulmont.cuba.gui.components.Action
import com.haulmont.cuba.gui.components.ListComponent
import com.haulmont.cuba.gui.components.TextField
import com.haulmont.cuba.gui.components.TextInputField
import com.haulmont.cuba.gui.components.Tree
import com.haulmont.cuba.gui.components.actions.BaseAction
import com.haulmont.cuba.gui.data.HierarchicalDatasource
import com.haulmont.cuba.gui.icons.CubaIcon
import com.haulmont.cuba.gui.icons.Icons

import javax.annotation.Nullable
import javax.inject.Inject
import java.util.function.Consumer

class DirectoryTree extends AbstractWindow {

    @Inject
    DirectoryService directoryService

    @Inject
    HierarchicalDatasource<Directory, UUID> directoriesDs

    @Inject
    Tree tree

    @Inject
    TextField startPath

    @Inject
    Icons icons


    @Override
    void init(Map<String, Object> params) {
        tree.setIconProvider(new ListComponent.IconProvider<Directory>() {
            @Nullable
            @Override
            String getItemIcon(Directory entity) {
                entity.getFile() ? icons.get(CubaIcon.FILE) : icons.get(CubaIcon.FOLDER)
            }
        })

        startPath.addEnterPressListener(new TextInputField.EnterPressListener() {
            @Override
            void enterPressed(TextInputField.EnterPressEvent e) {
                open()
            }
        })

        def openHandler = new BaseAction("open").withHandler(new Consumer<Action.ActionPerformedEvent>() {
            @Override
            void accept(Action.ActionPerformedEvent event) {
                open()
            }
        })
        tree.setEnterPressAction(openHandler)
        tree.setItemClickAction(openHandler)
    }

    void touch() {
        Directory directory = selectedDirectory()
        if (!directory.getFile()) {
            def filename = directoryService.touchFileInDirectory(directory)
            showNotification("File created: ${filename}", NotificationType.TRAY)
            openDirectory(startPath.value)
        } else {
            showNotification("File can only be created in a Directory", NotificationType.WARNING)
        }

    }

    void open() {
        Directory directory = selectedDirectory()
        if (directory && !directory.getFile()) {
            startPath.value = directory.path
            openDirectory(startPath.value)
        }
        else if (directory && directory.getFile()) {
            show()
        }
        else {
            openDirectory(startPath.value)
        }

    }

    private void openDirectory(startPath) {
        directoriesDs.refresh([startPath: startPath])
        tree.expandTree()
    }

    void show() {
        Directory directory = selectedDirectory()
        if (directory.getFile()) {
            showMessageDialog(
                    "File Content: " + directory.name,
                    directoryService.openFile(directory),
                    MessageType.CONFIRMATION
            )
        } else {
            startPath.value = directory.path
            openDirectory(startPath.value)
        }
    }

    private Directory selectedDirectory() {
        tree.singleSelected as Directory
    }

    void removeFile() {
        Directory directory = selectedDirectory()
        directoryService.removeFile(directory)
        tree.setSelected(directory.parent)
        openDirectory(startPath.value)
    }
}