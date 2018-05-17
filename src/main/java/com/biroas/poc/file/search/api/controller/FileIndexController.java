package com.biroas.poc.file.search.api.controller;

import com.biroas.poc.file.search.api.model.result.IndexResult;
import com.biroas.poc.file.search.api.service.FileIndexService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;

@RestController
@RequestMapping("/api/rest/v1/file/index")
public class FileIndexController {

    private FileIndexService fileIndexService;

    @Inject
    public FileIndexController(FileIndexService fileIndexService) {
        this.fileIndexService = fileIndexService;
    }


    @PostMapping
    public IndexResult indexFiles(@RequestParam(name = "path") String path,
                                  @RequestParam(name = "recursive", defaultValue = "false") boolean recursive) throws IOException {
        return fileIndexService.indexDirectory(path, recursive);
    }

    @DeleteMapping
    public IndexResult deleteAllFiles() {
        return fileIndexService.deleteAllFiles();
    }
}
