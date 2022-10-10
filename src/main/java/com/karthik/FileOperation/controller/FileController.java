package com.karthik.FileOperation.controller;

import com.karthik.FileOperation.entity.FileData;
import com.karthik.FileOperation.serviceImpl.FileServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/file/v1")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    FileServiceImpl fileService;

    @PostMapping("/storeFile")
    public ResponseEntity<?> storeFile(@RequestParam("file") MultipartFile file,
                                       @RequestParam("comment") String comment) throws IOException {

        FileData fileStatus = fileService.storeFile(file, comment);

        if (fileStatus == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("File didn't save successfully!!");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body("File saved successfully :" + fileStatus.getFileName());
    }

    @GetMapping("/viewFile/{id}")
    public ResponseEntity<?> viewFileData(@PathVariable Long id) {
        byte[] fileReceived = fileService.viewFile(id);

        if (fileReceived == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No Data to view for ID :" + id);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("application/pdf"))
                    .body(fileReceived);
        }
    }

    @GetMapping("/findFile/{id}")
    public ResponseEntity<?> findFileById(@PathVariable Long id) {
        Optional<FileData> fileReceived = fileService.findFileById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(fileReceived);
    }

    @GetMapping("/files")
    public ResponseEntity<?> getAllFiles() {
        List<FileData> fileList = fileService.getAllFile();

        if (fileList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No File's found to display!");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(fileList);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id) {
        FileData deleteResponse = fileService.deleteFile(id);

        if (deleteResponse == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No data found to delete for ID :" + id);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("File Delete Successfully for ID :" + id);
        }
    }
}
