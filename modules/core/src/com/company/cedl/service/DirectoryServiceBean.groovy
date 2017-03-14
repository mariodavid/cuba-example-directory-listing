package com.company.cedl.service

import com.company.cedl.entity.Directory
import com.haulmont.cuba.core.global.Metadata
import groovy.io.FileType
import org.springframework.stereotype.Service

import javax.inject.Inject

@Service(DirectoryService.NAME)
public class DirectoryServiceBean implements DirectoryService {

    @Inject
    Metadata metadata

    @Override
    Collection<Directory> listDirectories(String startPath) {

        Collection<Directory> result = []
        Map<String, Directory> fileDirectoryLookup = [:]

        def startFile = new File(startPath)
        createDirectory(result, fileDirectoryLookup, startFile)

        startFile.eachFileRecurse(FileType.DIRECTORIES) { file ->
            createDirectory(result, fileDirectoryLookup, file)
        }

        result
    }

    @Override
    void touchFileInDirectory(Directory directory) {
        new File(directory.path + "/testTouch.txt").createNewFile()

    }

    protected void createDirectory(Collection<Directory> result, Map<String, Directory> fileDirectoryLookup, File file) {
        def newDirectory = metadata.create(Directory)
        newDirectory.name = file.name
        newDirectory.path = file.absolutePath
        newDirectory.parent = fileDirectoryLookup[file.parentFile.absolutePath] ?: null

        fileDirectoryLookup[(file.absolutePath)] = newDirectory

        result << newDirectory
    }
}