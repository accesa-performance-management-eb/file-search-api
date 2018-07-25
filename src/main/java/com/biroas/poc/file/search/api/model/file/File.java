package com.biroas.poc.file.search.api.model.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "poc-files", type = "file")
public class File {
    @Id
    private String id;
    private String fileName;
    private boolean isDirectory;
    //TODO study the right way to make parentDirectory searchable when having also separators in name
    //@Field(index = FieldIndex.not_analyzed, type = FieldType.String)
    private String parentDirectory;
    private FileAttributes fileAttributes = new FileAttributes();
    private FileType fileType = new FileType();
    private String systemName;
    private String fileContent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @JsonProperty(value = "isDirectory")
    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public String getParentDirectory() {
        return parentDirectory;
    }

    public void setParentDirectory(String parentDirectory) {
        this.parentDirectory = parentDirectory;
    }

    public FileAttributes getFileAttributes() {
        return fileAttributes;
    }

    public void setFileAttributes(FileAttributes fileAttributes) {
        this.fileAttributes = fileAttributes;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" Id: ").append(this.id);
        stringBuilder.append(", FileName: ").append(this.fileName);
        stringBuilder.append(", FileType: ").append(this.fileType);
        stringBuilder.append(", ParentDirectory: ").append(this.parentDirectory);
        stringBuilder.append(", SystemName: ").append(this.systemName);
        return stringBuilder.toString();
    }
}
