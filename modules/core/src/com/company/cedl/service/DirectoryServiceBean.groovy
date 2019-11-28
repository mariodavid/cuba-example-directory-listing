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

        createDirectory(result, fileDirectoryLookup, startFile.parentFile, "..")

        createDirectory(result, fileDirectoryLookup, startFile)

        startFile.eachFileRecurse(FileType.ANY) { file ->
            createDirectory(result, fileDirectoryLookup, file)
        }

        result
    }

    @Override
    String touchFileInDirectory(Directory directory) {
        def filename = "test-file-${new Random().nextInt(1000000)}.txt"
        def newFile = new File(directory.path + "/" + filename)

            newFile.createNewFile()

        newFile.write("""
Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.
""")

        filename
    }

    @Override
    String openFile(Directory file) {
        new File(file.path).text
    }

    @Override
    void removeFile(Directory file) {
        new File(file.path).delete()
    }

    protected void createDirectory(Collection<Directory> result, Map<String, Directory> fileDirectoryLookup, File file, String directoryName = null) {
        def newDirectory = metadata.create(Directory)
        newDirectory.name = directoryName ?: file.name
        newDirectory.path = file.absolutePath
        newDirectory.parent = fileDirectoryLookup[file.parentFile.absolutePath] ?: null
        newDirectory.file = file.isFile()

        fileDirectoryLookup[(file.absolutePath)] = newDirectory

        result << newDirectory
    }
}