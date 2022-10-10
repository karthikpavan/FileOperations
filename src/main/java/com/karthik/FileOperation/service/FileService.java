package com.karthik.FileOperation.service;

import com.karthik.FileOperation.entity.FileData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface FileService {

    public FileData storeFile(MultipartFile file, String comment) throws IOException;

    public byte[] viewFile(Long id);

    public List<FileData> getAllFile();

    public FileData deleteFile(Long id);

    public FileData saveFile(FileData fileData);

    Optional<FileData> findFileById(Long id);

    void deleteById(long id);

}
