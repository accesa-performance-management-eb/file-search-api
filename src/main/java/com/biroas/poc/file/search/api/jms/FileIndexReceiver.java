package com.biroas.poc.file.search.api.jms;

import com.biroas.poc.file.search.api.model.file.File;
import com.biroas.poc.file.search.api.service.FileIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

import javax.inject.Inject;

public class FileIndexReceiver {

    private final Logger logger = LoggerFactory.getLogger(FileIndexReceiver.class);

    @Inject
    private FileIndexService fileIndexService;

    @JmsListener(destination = "${activemq.queue.name}")
    public void receiveFileIndexRequest(File file){
        logger.info("Received file index request: {}", file);
        fileIndexService.indexFile(file);
    }
}
