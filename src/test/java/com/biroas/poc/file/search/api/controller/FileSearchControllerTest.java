package com.biroas.poc.file.search.api.controller;

import com.biroas.poc.file.search.api.FileSearchApplication;
import com.biroas.poc.file.search.api.model.file.File;
import com.biroas.poc.file.search.api.model.result.SearchResult;
import com.biroas.poc.file.search.api.service.FileSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.inject.Inject;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileSearchApplication.class)
@AutoConfigureMockMvc
public class FileSearchControllerTest {

    @Inject
    private MockMvc mockMvc;
    @MockBean
    FileSearchService fileSearchService;
    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void findFilesTest() throws Exception {
        String url = "/api/rest/v1/file/search";
        int numberOfResults = 10;

        SearchResult searchResult = getSearchResult(numberOfResults);

        Mockito.when(fileSearchService.find(Matchers.anyObject(), Matchers.anyObject())).thenReturn(searchResult);

        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count", CoreMatchers.is(numberOfResults)));
    }

    @Test
    public void findFilesByContent() throws Exception {
        String url = "/api/rest/v1/file/search/content";
        int numberOfResults = 10;

        SearchResult searchResult = getSearchResult(numberOfResults);

        Mockito.when(fileSearchService.findByFileContent(Matchers.anyObject(), Matchers.anyObject())).thenReturn(searchResult);

        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count", CoreMatchers.is(numberOfResults)));
    }

    private SearchResult getSearchResult(int numberOfResults) {
        SearchResult searchResult = new SearchResult();

        searchResult.setCount(10);
        searchResult.setFiles(new ArrayList<>());

        for (int i = 0; i < numberOfResults; i++) {
            File file = new File();
            file.setFileName("file" + i);

            searchResult.getFiles().add(file);
        }

        return searchResult;
    }
}
