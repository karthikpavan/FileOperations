package com.karthik.FileOperation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karthik.FileOperation.entity.FileData;
import com.karthik.FileOperation.entity.Post;
import com.karthik.FileOperation.repository.FileRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FileControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(FileControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FileRepository fileRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void shouldcreateFile() throws Exception {

        logger.info("--- Sample Data preparation ---");
        File file = ResourceUtils.getFile("classpath:fileContentForTest.text");
        InputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "fileContentForTest.text",
                MediaType.MULTIPART_FORM_DATA_VALUE, inputStream);
        
        Mockito.when(fileRepository.save(Mockito.any(FileData.class))).thenReturn(new FileData());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/file/v1/storeFile")
                        .file(multipartFile)
                        .param("comment", "first comment"))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    public void shouldReturnFile() throws Exception {

        logger.info("--- Sample Data preparation ---");
        File file = ResourceUtils.getFile("classpath:fileContentForTest.text");
        InputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "fileContentForTest.text",
                MediaType.MULTIPART_FORM_DATA_VALUE, inputStream);

        FileData fileData = new FileData(3l, "File1", "application/pdf",
                multipartFile.getBytes(),
                Post.builder().post_id(2l).comment("first comment").build());

        logger.info("---> mocking  --->");
        Mockito.when(fileRepository.findById(3l)).thenReturn(Optional.of(fileData));
        mockMvc.perform(get("/api/file/v1/findFile/{id}", 3l))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.file_id").value(fileData.getFile_id()))
                .andExpect(jsonPath("$.fileName").value(fileData.getFileName()))
                .andExpect(jsonPath("$.fileType").value(fileData.getFileType()))
                .andDo(print());
        logger.info("<--- mocking <---");

    }

    @Test
    public void shouldReturnFileNotFound() throws Exception {

        Mockito.when(fileRepository.findById(1l)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/file/v1/findFile/{id}", 1l))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void shouldReturnListofFiles() throws Exception {

        List<FileData> fileDataList = new ArrayList<>(
                Arrays.asList(FileData.builder().fileName("File1").fileType("application/pdf").
                                fileContent(null)
                                .post(Post.builder().post_id(1l).comment("Post1").build()).build(),
                        FileData.builder().fileName("File2").fileType("application/pdf")
                                .fileContent(null)
                                .post(Post.builder().post_id(2l).comment("Post2").build()).build(),
                        FileData.builder().fileName("File1").fileType("application/pdf")
                                .fileContent(null).post(Post.builder().post_id(3l).comment("Post3").build()).build()
                ));

        Mockito.when(fileRepository.findAll()).thenReturn(fileDataList);
        mockMvc.perform(get("/api/file/v1/files"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(fileDataList.size()))
                .andDo(print());
    }

    @Test
    public void shouldDeleteFile() throws Exception {

        Mockito.doNothing().when(fileRepository).deleteById(1l);
        mockMvc.perform(delete("/api/file/v1/delete/{id}", 1l))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}
