package com.biroas.poc.file.search.api.controller;

import com.biroas.poc.file.search.api.FileSearchApplication;
import com.biroas.poc.file.search.api.model.file.File;
import com.biroas.poc.file.search.api.model.result.IndexResult;
import com.biroas.poc.file.search.api.service.FileIndexService;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileSearchApplication.class)
@AutoConfigureMockMvc
public class FileIndexControllerTest {
    @Inject
    private MockMvc mockMvc;
    @MockBean
    FileIndexService fileIndexService;
    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void indexFileTest() throws Exception {
        String url = "/api/rest/v1/file/index";

        File file = new File();

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(file)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.indexedDocuments", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDocuments", CoreMatchers.is(0)));
    }

    @Test
    public void indexInternalFilesTest() throws Exception {
        String url = "/api/rest/v1/file/index/internal";
        String path = "/path/with/40/files";

        IndexResult indexResult = new IndexResult();
        indexResult.setIndexedDocuments(40);

        Mockito.when(fileIndexService.indexDirectory(Matchers.anyString(), Matchers.anyBoolean())).thenReturn(indexResult);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .param("path", path))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.indexedDocuments", CoreMatchers.is(40)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDocuments", CoreMatchers.is(0)));
    }

    @Test
    public void deleteAllFilesTest() throws Exception {
        String url = "/api/rest/v1/file/index";

        IndexResult indexResult = new IndexResult();
        indexResult.setDeletedDocuments(40);

        Mockito.when(fileIndexService.deleteAllFiles()).thenReturn(indexResult);

        mockMvc.perform(MockMvcRequestBuilders.delete(url))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.indexedDocuments", CoreMatchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDocuments", CoreMatchers.is(40)));
    }


}
