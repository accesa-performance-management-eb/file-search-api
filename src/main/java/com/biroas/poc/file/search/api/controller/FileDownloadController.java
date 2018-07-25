package com.biroas.poc.file.search.api.controller;

import com.biroas.poc.file.search.api.jms.FileDownloadRequestSender;
import com.biroas.poc.file.search.api.model.file.File;
import com.biroas.poc.file.search.api.service.FileSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/rest/v1/file/download")
public class FileDownloadController {

    @Inject
    private FileSearchService fileSearchService;
    @Inject
    private FileDownloadRequestSender fileDownloadRequestSender;

    @GetMapping(path = "/{fileId}")
    public void downloadFile(@PathVariable(name = "fileId") String fileId) {

        File file = fileSearchService.findById(fileId);
        fileDownloadRequestSender.sendFileDownloadRequest(file);

    }
}
