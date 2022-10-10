package com.karthik.FileOperation.serviceImpl;

import com.karthik.FileOperation.entity.FileData;
import com.karthik.FileOperation.entity.Post;
import com.karthik.FileOperation.exception.FileNotFoundException;
import com.karthik.FileOperation.exception.PostNotFoundException;
import com.karthik.FileOperation.repository.FileRepository;
import com.karthik.FileOperation.repository.PostRepository;
import com.karthik.FileOperation.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    FileRepository fileRepository;

    @Autowired
    PostRepository postRepository;

    @Override
    public FileData storeFile(MultipartFile file, String comment) throws IOException {

        logger.info("File Content ==> " + file.getBytes());
        logger.info("File Content length ==> " + file.getBytes().length);
        String fileContentData = new String(file.getBytes());
//        logger.info("File Content String ==> "+fileContentData); large data

        FileData fileData = fileRepository.save(FileData.builder()
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .fileContent(file.getBytes())
                .post(postRepository.save(Post.builder()
                        .comment(comment)
                        .build()))
                .build());

        if (fileData != null) {
            return fileData;
        } else {
            return null;
        }
    }

    @Override
    public byte[] viewFile(Long id) {
        Optional<FileData> dbFileData = Optional.ofNullable(fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException(id)));

        return dbFileData.get().getFileContent();

//        if(dbFileData != null){
//            return dbFileData.get().getFileContent();
//        } else {
//            return null;
//        }
    }

    @Override
    public List<FileData> getAllFile() {
        List<FileData> allFiles = fileRepository.findAll();

        return allFiles;

//        if(allFiles.isEmpty()) {
//            throw new FileNotFoundException("No File's available");
//        } else {
//            return allFiles;
//        }
    }

    @Override
    public FileData deleteFile(Long id) {
        FileData file = fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException(id));

        Post post = postRepository.findById(file.getPost().getPost_id()).
                orElseThrow(() -> new PostNotFoundException(file.getPost().getPost_id()));

        if (file != null && post != null) {

            fileRepository.delete(file);
            postRepository.delete(post);

            return file;
        }
        return null;
    }

    @Override
    public FileData saveFile(FileData fileData) {

        return fileRepository.save(fileData);
//        if(fileData != null) {
//            return fileRepository.save(fileData);
//        } else {
//            return null;
//        }
    }

    @Override
    public Optional<FileData> findFileById(Long id) {
        Optional<FileData> fileIdData = Optional.ofNullable(fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException(id)));

        return fileIdData;
    }

    @Override
    public void deleteById(long id) {
        fileRepository.deleteById(id);
    }

}
