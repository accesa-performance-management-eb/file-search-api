package com.biroas.poc.file.search.api.jms;

import com.biroas.poc.file.search.api.FileSearchApplication;
import com.biroas.poc.file.search.api.model.file.File;
import com.biroas.poc.file.search.api.service.FileIndexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileSearchApplication.class)
public class FileIndexReceiverTest {

    @MockBean
    FileIndexService fileIndexService;

    @Inject
    FileIndexReceiver fileIndexReceiver;

    @Test
    public void receiveFileIndexRequestTest() {
        File file = new File();

        fileIndexReceiver.receiveFileIndexRequest(file);

        Mockito.verify(fileIndexService).indexFile(file);

    }
}
