package com.karthik.FileOperation.integration;

import com.karthik.FileOperation.entity.FileData;
import com.karthik.FileOperation.entity.Post;
import com.karthik.FileOperation.repository.FileRepository;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileControllerTests {

    private static final Logger logger = LoggerFactory.getLogger(FileControllerTests.class);

    @LocalServerPort
    private int port;

    private String baseURL = "http://localhost";
    @Autowired
    private static RestTemplate restTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    FileRepository fileRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setup() {
        baseURL = baseURL.concat(":").concat(port + "").concat("/api/file/v1");
    }

    @Test
    @DisplayName("Save File")
    public void testSaveFile() throws Exception {

        File file = ResourceUtils.getFile("classpath:fileContentForTest.text");
        InputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "fileContentForTest.text",
                MediaType.MULTIPART_FORM_DATA_VALUE, inputStream);

        FileData fileData = new FileData(2l, "File1", "application/pdf",
                multipartFile.getBytes(),
                Post.builder().post_id(1l).comment("first comment").build());
        Assertions.assertNotNull(fileData);

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart(baseURL + "/storeFile")
                        .file(multipartFile)
                        .param("comment", "first comment"))
                .andExpect(status().is(200));
        logger.info("saveFile URL ==> " + baseURL);

        logger.info("Data in H2 DB ==>" + fileRepository.findAll().size());
        Assertions.assertEquals(1, fileRepository.findAll().size());

        //      removing the first added data for continuous CRUD testing
        restTemplate.delete(baseURL.concat("/delete/{id}"), 2);
        Assertions.assertEquals(0, fileRepository.findAll().size());
    }

    @Test
    @DisplayName("Get All Files")
    @Sql(statements = "insert into post(comment, post_id) values('first comment', 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into files (file_content, file_name, file_type, post_id, file_id) values (null, 'file1', 'pdf', 1, 2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM FILES WHERE FILE_ID = 2", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM POST WHERE POST_ID = 1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetFiles() {

        List<FileData> response = restTemplate.getForObject(baseURL + "/files", List.class);
        logger.info("getAllFile URL ==> " + baseURL);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(1, fileRepository.findAll().size());
    }

    @Test
    @DisplayName("Get File by ID")
    @Sql(statements = "insert into post(comment, post_id) values('Secong comment', 2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into files (file_content, file_name, file_type, post_id, file_id) values (null, 'file2', 'pdf', 2, 3)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM FILES WHERE FILE_ID = 3", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM POST WHERE POST_ID = 2", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testfindFileById() {

        FileData response = restTemplate.getForObject(baseURL.concat("/findFile/{id}"), FileData.class, 3);
        logger.info("Find BY ID URL ==> " + baseURL.concat("/findFile/"));

        Assertions.assertAll(
                () -> Assertions.assertNotNull(response),
                () -> Assertions.assertEquals(3, response.getFile_id()),
                () -> Assertions.assertEquals("file2", response.getFileName()),
                () -> Assertions.assertEquals("pdf", response.getFileType())
        );
    }

    @Test
    @DisplayName("Delete by ID")
    @Sql(statements = "insert into post(comment, post_id) values('Third comment', 2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into files (file_content, file_name, file_type, post_id, file_id) values (null, 'file3', 'pdf', 2, 4)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testDeleteByID() {

        int dataCount = fileRepository.findAll().size();
        Assertions.assertEquals(1, dataCount);

        restTemplate.delete(baseURL.concat("/delete/{id}"), 4);
        Assertions.assertEquals(0, fileRepository.findAll().size());

    }
}
