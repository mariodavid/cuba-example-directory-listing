package com.company.cedl.service;


import com.company.cedl.entity.Directory;

import java.util.Collection;

public interface DirectoryService {
    String NAME = "cedl_DirectoryService";

    Collection<Directory> listDirectories(String startPath);

    String touchFileInDirectory(Directory directory);
    String openFile(Directory file);
    void removeFile(Directory file);
}