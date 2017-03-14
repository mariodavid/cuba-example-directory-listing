package com.company.cedl.service;


import com.company.cedl.entity.Directory;

import java.util.Collection;

public interface DirectoryService {
    String NAME = "cedl_DirectoryService";

    Collection<Directory> listDirectories(String startPath);

    void touchFileInDirectory(Directory directory);
}