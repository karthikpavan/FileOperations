package com.karthik.FileOperation.service;

import com.karthik.FileOperation.entity.FileData;
import com.karthik.FileOperation.entity.Post;
import com.karthik.FileOperation.repository.FileRepository;
import com.karthik.FileOperation.repository.PostRepository;
import com.karthik.FileOperation.serviceImpl.FileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceTest.class);

    @Mock
    FileRepository fileRepository;

    @Mock
    PostRepository postRepository;

    @InjectMocks
    FileServiceImpl fileService;

    private FileData fileData;

    MockMultipartFile multipartFile;

    @BeforeEach
    public void setup() throws IOException {
        logger.info("---> Sample Data preparation --->");
        File file = ResourceUtils.getFile("classpath:fileContentForTest.text");
        InputStream inputStream = new FileInputStream(file);
        multipartFile = new MockMultipartFile("file", "fileContentForTest.text",
                MediaType.MULTIPART_FORM_DATA_VALUE, inputStream);

        fileData = new FileData(3l, "Sample File1", "application/pdf",
                multipartFile.getBytes(),
                Post.builder().post_id(2l).comment("Sample first comment").build());

        logger.info("Sample Data :" + fileData.getFile_id() + ","
                + fileData.getFileName() + ", " + fileData.getFileType() + ", " + fileData.getFileContent()
                + ", " + fileData.getPost().getPost_id() + ", " + fileData.getPost().getComment());
        logger.info("<--- Sample Data preparation <---");
    }

    @Test
    public void saveFile() throws IOException {

//        given
        when(fileRepository.save(any(FileData.class))).thenReturn(fileData);

//        when
        FileData savedFile = fileService.storeFile(multipartFile, "adding service layer test");

//        then
        assertThat(savedFile).isNotNull();
    }


    @Test
    public void getFileList() throws IOException {

        // given
        FileData fileData1 = FileData.builder()
                .file_id(25l)
                .fileName("list file1")
                .fileType("pdf")
                .fileContent(multipartFile.getBytes())
                .post(Post.builder().post_id(6l).comment("list comment1").build())
                .build();

        given(fileRepository.findAll()).willReturn(Arrays.asList(fileData, fileData1));

        // when
        List<FileData> fileDataList = fileService.getAllFile();

        // then
        assertThat(fileDataList).isNotNull();
        assertThat(fileDataList.size()).isEqualTo(2);
    }

    @Test
    public void getFileById() {
        // given
        given(fileRepository.findById(3L)).willReturn(Optional.of(fileData));

        // when
        FileData fileId = fileService.findFileById(fileData.getFile_id()).get();

        // then
        assertThat(fileId).isNotNull();
    }

    @Test
    public void deleteFileById() {
//        given
        doNothing().when(fileRepository).deleteById(3l);

//        when
        fileService.deleteById(3l);

//        then
        verify(fileRepository, times(1)).deleteById(3l);
    }
}
