package com.biroas.poc.file.search.api.service;

import com.biroas.poc.file.search.api.model.file.File;
import com.biroas.poc.file.search.api.model.file.FileAttributes;
import com.biroas.poc.file.search.api.model.file.FileType;
import com.biroas.poc.file.search.api.model.result.IndexResult;
import com.biroas.poc.file.search.api.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

@Service
public class FileIndexService {

    private FileRepository fileRepository;

    @Inject
    public FileIndexService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File indexFile(File file) {
        return fileRepository.save(file);
    }

    public IndexResult indexDirectory(Path path, boolean recursive) throws IOException {
        IndexResult indexResult = new IndexResult();

        java.io.File folder = path.toFile();
        java.io.File[] listOfFiles = folder.listFiles();
        long count = 0;

        if (ObjectUtils.isEmpty(listOfFiles)) {
            return indexResult;
        }

        for (java.io.File diskFile : listOfFiles) {
            File file = new File();
            file.setFileName(diskFile.getName());
            file.setParentDirectory(diskFile.getParentFile().getAbsolutePath());
            file.setDirectory(diskFile.isDirectory());
            if (recursive && file.isDirectory()) {
                count += indexDirectory(path.getFileSystem().getPath(
                        file.getParentDirectory() + java.io.File.separator + file.getFileName()), true)
                        .getIndexedDocuments();
            }

            if (!file.isDirectory()) {
                file.setFileAttributes(getFileAttributes(diskFile));
                file.setFileType(getFileType(diskFile));
            }

            this.indexFile(file);
            count++;
        }

        indexResult.setIndexedDocuments(count);
        return indexResult;
    }

    public IndexResult deleteAllFiles() {
        IndexResult indexResult = new IndexResult();
        indexResult.setDeletedDocuments(fileRepository.count());
        fileRepository.deleteAll();
        return indexResult;
    }

    private FileAttributes getFileAttributes(java.io.File file) throws IOException {
        FileAttributes fileAttributes = new FileAttributes();
        BasicFileAttributes basicFileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);

        fileAttributes.setCreationDate(new Date(basicFileAttributes.creationTime().toMillis()));
        fileAttributes.setLastModifiedDate(new Date(basicFileAttributes.lastModifiedTime().toMillis()));
        fileAttributes.setLastAccessDate(new Date(basicFileAttributes.lastAccessTime().toMillis()));
        fileAttributes.setSize(basicFileAttributes.size());
        return fileAttributes;
    }

    private FileType getFileType(java.io.File file) {
        FileType fileType = new FileType();
        String extension = com.google.common.io.Files.getFileExtension(file.getName());
        fileType.setExtension(extension);
        return fileType;
    }
}
