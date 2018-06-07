package com.biroas.poc.file.search.api.service;

import com.biroas.poc.file.search.api.model.file.File;
import com.biroas.poc.file.search.api.model.result.SearchResult;
import com.biroas.poc.file.search.api.repository.FileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class FileSearchService {

    private FileRepository fileRepository;

    @Inject
    public FileSearchService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File save(File file) {
        return fileRepository.save(file);
    }

    public Iterable<File> findAll() {
        return fileRepository.findAll();
    }

    public SearchResult findAll(Pageable paging) {
        Page<File> filePage = fileRepository.findAll(paging);

        return getSearchResult(filePage);
    }

    public SearchResult findByFileName(String fileName, Pageable paging) {
        Page<File> filePage = fileRepository.findByFileNameContaining(fileName, paging);
        return getSearchResult(filePage);
    }

    public SearchResult findByFileNameAndParentDir(String fileName, String parentDir, Pageable paging) {
        Page<File> filePage = fileRepository.findByFileNameContainingAndParentDirectoryContaining(fileName, parentDir, paging);
        return getSearchResult(filePage);
    }

    public SearchResult findByFileContent(String fileContent, Pageable paging) {
        Page<File> filePage = fileRepository.findByFileNameContainingOrFileContentContaining(fileContent, fileContent, paging);
        return getSearchResult(filePage);
    }

    private SearchResult getSearchResult(Page<File> filePage) {
        SearchResult searchResult = new SearchResult();
        searchResult.setFiles(filePage.getContent());
        searchResult.setCount(filePage.getContent().size());
        searchResult.setPageSize(filePage.getSize());
        searchResult.setPageNumber(filePage.getNumber() + 1);
        searchResult.setTotalPages(filePage.getTotalPages());
        return searchResult;
    }
}
