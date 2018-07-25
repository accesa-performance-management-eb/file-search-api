package com.biroas.poc.file.search.api.controller;

import com.biroas.poc.file.search.api.model.query.QueryFilter;
import com.biroas.poc.file.search.api.model.result.SearchResult;
import com.biroas.poc.file.search.api.service.FileSearchService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/rest/v1/file/search")
public class FileSearchController {

    private FileSearchService fileSearchService;

    @InitBinder
    public void initBinder(final WebDataBinder binder){
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @Inject
    public FileSearchController(FileSearchService fileSearchService) {
        this.fileSearchService = fileSearchService;
    }

    @GetMapping
    public SearchResult findFiles(QueryFilter filter,
                                  @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                  @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize);
        return fileSearchService.find(filter, pageRequest);
    }

    @GetMapping(path = "/content")
    public SearchResult findFilesByContent(@RequestParam(name = "fileContent", defaultValue = "") String fileContent,
                                  @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                  @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize);
        return fileSearchService.findByFileContent(fileContent, pageRequest);
    }
}
