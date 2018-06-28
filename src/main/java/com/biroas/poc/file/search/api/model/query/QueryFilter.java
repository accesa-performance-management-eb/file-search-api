package com.biroas.poc.file.search.api.model.query;

public class QueryFilter {

    private String fileName;
    private String parentDirectory;
    private String content;
    private String systemName;
    private RangeNumeric sizeRange;
    private RangeDate createdRange;
    private RangeDate modifiedRange;
    private RangeDate accessedRange;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getParentDirectory() {
        return parentDirectory;
    }

    public void setParentDirectory(String parentDirectory) {
        this.parentDirectory = parentDirectory;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public RangeNumeric getSizeRange() {
        return sizeRange;
    }

    public void setSizeRange(RangeNumeric sizeRange) {
        this.sizeRange = sizeRange;
    }

    public RangeDate getCreatedRange() {
        return createdRange;
    }

    public void setCreatedRange(RangeDate createdRange) {
        this.createdRange = createdRange;
    }

    public RangeDate getModifiedRange() {
        return modifiedRange;
    }

    public void setModifiedRange(RangeDate modifiedRange) {
        this.modifiedRange = modifiedRange;
    }

    public RangeDate getAccessedRange() {
        return accessedRange;
    }

    public void setAccessedRange(RangeDate accessedRange) {
        this.accessedRange = accessedRange;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("fileName: ").append(fileName);
        stringBuilder.append(" parentDirectory: ").append(parentDirectory);
        stringBuilder.append(" content: ").append(content);
        stringBuilder.append(" systemName: ").append(systemName);
        stringBuilder.append(" sizeRange: ").append(sizeRange);
        stringBuilder.append(" createdRange: ").append(createdRange);
        stringBuilder.append(" modifiedRange: ").append(modifiedRange);
        stringBuilder.append(" accessedRange: ").append(accessedRange);
        return stringBuilder.toString();
    }

}
