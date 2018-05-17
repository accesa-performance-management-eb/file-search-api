package com.biroas.poc.file.search.api.controller;

import com.biroas.poc.file.search.api.model.result.SearchResult;
import com.biroas.poc.file.search.api.service.FileSearchService;
import org.apache.lucene.queryparser.flexible.standard.QueryParserUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/rest/v1/file/search")
public class FileSearchController {

    private FileSearchService fileSearchService;

    @Inject
    public FileSearchController(FileSearchService fileSearchService) {
        this.fileSearchService = fileSearchService;
    }

    @GetMapping
    public SearchResult findFiles(@RequestParam(name = "fileName", defaultValue = "") String fileName,
                                  @RequestParam(name = "parentDir", defaultValue = "") String parentDir,
                                  @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                  @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize);
        return fileSearchService.findByFileNameAndParentDir(fileName, QueryParserUtil.escape(parentDir), pageRequest);
    }
}
