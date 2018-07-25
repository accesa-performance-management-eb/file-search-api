package com.biroas.poc.file.search.api.service;

import com.biroas.poc.file.search.api.FileSearchApplication;
import com.biroas.poc.file.search.api.model.file.File;
import com.biroas.poc.file.search.api.model.query.QueryFilter;
import com.biroas.poc.file.search.api.model.query.RangeDate;
import com.biroas.poc.file.search.api.model.query.RangeNumeric;
import com.biroas.poc.file.search.api.model.result.SearchResult;
import com.biroas.poc.file.search.api.repository.FileRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileSearchApplication.class)
public class FileSearchServiceTest {

    @Inject
    FileSearchService fileSearchService;
    @MockBean
    FileRepository fileRepository;
    @MockBean
    ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void findTest() {
        int totalFile = 40;
        QueryFilter filter = new QueryFilter();

        RangeDate rangeDate = new RangeDate();
        rangeDate.setFrom(new Date());
        rangeDate.setTo(new Date());

        RangeNumeric rangeNumeric=new RangeNumeric();
        rangeNumeric.setFrom(0);
        rangeNumeric.setTo(10);

        filter.setAccessedRange(rangeDate);
        filter.setContent("content");
        filter.setCreatedRange(rangeDate);
        filter.setFileName("filename");
        filter.setModifiedRange(rangeDate);
        filter.setParentDirectory("parentDirectory");
        filter.setSizeRange(rangeNumeric);
        filter.setSystemName("systemName");

        List<File> fileList = new ArrayList<>();

        for (int i = 0; i < totalFile; i++) {
            fileList.add(new File());
        }

        AggregatedPage filePage = new AggregatedPageImpl(fileList);

        Mockito.when(elasticsearchTemplate.queryForPage(Matchers.any(SearchQuery.class), Matchers.eq(File.class))).thenReturn(filePage);

        SearchResult searchResult = fileSearchService.find(filter, new PageRequest(1, 10));

        Assert.assertEquals(totalFile, searchResult.getCount());
        Assert.assertEquals(totalFile, searchResult.getFiles().size());

    }

    @Test
    public void findAllTest(){
        int totalFile = 40;

        List<File> fileList = new ArrayList<>();

        for (int i = 0; i < totalFile; i++) {
            fileList.add(new File());
        }

        AggregatedPage filePage = new AggregatedPageImpl(fileList);

        Mockito.when(fileRepository.findAll(Matchers.any(Pageable.class))).thenReturn(filePage);

        SearchResult searchResult = fileSearchService.findAll(new PageRequest(1, 10));

        Assert.assertEquals(totalFile, searchResult.getCount());
        Assert.assertEquals(totalFile, searchResult.getFiles().size());
    }

    @Test
    public void findByFileContentTest(){
        int totalFile = 40;
        String fileContent="content";

        List<File> fileList = new ArrayList<>();

        for (int i = 0; i < totalFile; i++) {
            fileList.add(new File());
        }

        AggregatedPage filePage = new AggregatedPageImpl(fileList);

        Mockito.when(fileRepository.findByFileNameContainingOrFileContentContaining(Matchers.anyString(),Matchers.anyString(),Matchers.any(Pageable.class))).thenReturn(filePage);

        SearchResult searchResult = fileSearchService.findByFileContent(fileContent,new PageRequest(1, 10));

        Assert.assertEquals(totalFile, searchResult.getCount());
        Assert.assertEquals(totalFile, searchResult.getFiles().size());
    }

    @Test
    public void saveTest(){
        File file=new File();
        file.setFileName("filename");
        file.setId("id1");
        file.setFileContent("content");
        file.setDirectory(false);
        file.setParentDirectory("parentDirectory");

        Mockito.when(fileRepository.save(file)).thenReturn(file);

        File savedFile = fileSearchService.save(file);

        Assert.assertEquals(file.getFileName(), savedFile.getFileName());
        Assert.assertEquals(file.getId(), savedFile.getId());
        Assert.assertEquals(file.getFileContent(), savedFile.getFileContent());
        Assert.assertEquals(file.isDirectory(), savedFile.isDirectory());
        Assert.assertEquals(file.getParentDirectory(), savedFile.getParentDirectory());

    }
}
