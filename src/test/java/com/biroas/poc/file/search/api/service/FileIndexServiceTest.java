package com.biroas.poc.file.search.api.service;

import com.biroas.poc.file.search.api.FileSearchApplication;
import com.biroas.poc.file.search.api.model.file.File;
import com.biroas.poc.file.search.api.model.result.IndexResult;
import com.biroas.poc.file.search.api.repository.FileRepository;
import org.elasticsearch.client.Client;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileSearchApplication.class)
public class FileIndexServiceTest {

    @Inject
    FileIndexService fileIndexService;
    @MockBean
    Client client;
    @MockBean
    FileRepository fileRepository;
    @MockBean
    ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void deleteAllFilesTest() {
        long totalFiles = 40;
        Mockito.when(fileRepository.count()).thenReturn(totalFiles);

        IndexResult indexResult = fileIndexService.deleteAllFiles();

        Assert.assertEquals(totalFiles, indexResult.getDeletedDocuments());
    }

    @Test
    public void indexFileTest() {
        long totalFiles = 40;
        Mockito.when(fileRepository.count()).thenReturn(totalFiles);

        File file = new File();
        file.setFileName("filename");
        file.setId("id1");
        file.setFileContent("content");
        file.setDirectory(false);
        file.setParentDirectory("parentDirectory");

        Mockito.when(fileIndexService.indexFile(file)).thenReturn(file);

        File indexedFile = fileIndexService.indexFile(file);

        Assert.assertEquals(file.getFileName(), indexedFile.getFileName());
        Assert.assertEquals(file.getId(), indexedFile.getId());
        Assert.assertEquals(file.getFileContent(), indexedFile.getFileContent());
        Assert.assertEquals(file.isDirectory(), indexedFile.isDirectory());
        Assert.assertEquals(file.getParentDirectory(), indexedFile.getParentDirectory());
    }

    @Test
    public void indexDirectoryTest() throws IOException {
        TemporaryFolder temporaryFolder=new TemporaryFolder();
        temporaryFolder.create();
        int count=0;

        for(int i=0;i<40;i++){
            temporaryFolder.newFile();
            count++;
            temporaryFolder.newFolder().createNewFile();
            count++;
        }

        IndexResult indexResult = fileIndexService.indexDirectory(temporaryFolder.getRoot().toPath(), true);
        Assert.assertEquals(count,indexResult.getIndexedDocuments());
        Assert.assertEquals(0,indexResult.getDeletedDocuments());
    }

    @Test
    public void indexDirectoryEmptyDirTest() throws IOException {
        TemporaryFolder temporaryFolder=new TemporaryFolder();
        temporaryFolder.create();

        IndexResult indexResult = fileIndexService.indexDirectory(temporaryFolder.newFolder().toPath(), true);

        Assert.assertEquals(0,indexResult.getIndexedDocuments());
        Assert.assertEquals(0,indexResult.getDeletedDocuments());
    }
}
