package com.karthik.FileOperation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "files")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long file_id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] fileContent;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

//    public FileData() {
//
//    }
//
//    public FileData(Long file_id, String fileName, String fileType, byte[] fileContent, Post post) {
//        this.file_id = file_id;
//        this.fileName = fileName;
//        this.fileType = fileType;
//        this.fileContent = fileContent;
//        this.post = post;
//    }
//
//    public Long getFile_id() {
//        return file_id;
//    }
//
//    public void setFile_id(Long file_id) {
//        this.file_id = file_id;
//    }
//
//    public String getFileName() {
//        return fileName;
//    }
//
//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }
//
//    public String getFileType() {
//        return fileType;
//    }
//
//    public void setFileType(String fileType) {
//        this.fileType = fileType;
//    }
//
//    public byte[] getFileContent() {
//        return fileContent;
//    }
//
//    public void setFileContent(byte[] fileContent) {
//        this.fileContent = fileContent;
//    }
//
//    public Post getPost() {
//        return post;
//    }
//
//    public void setPost(Post post) {
//        this.post = post;
//    }
//
//    @Override
//    public String toString() {
//        return "FileData{" +
//                "file_id=" + file_id +
//                ", fileName='" + fileName + '\'' +
//                ", fileType='" + fileType + '\'' +
//                ", post=" + post +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        FileData fileData = (FileData) o;
//        return getFile_id().equals(fileData.getFile_id()) && getFileName().equals(fileData.getFileName()) && getFileType().equals(fileData.getFileType()) && Arrays.equals(getFileContent(), fileData.getFileContent()) && getPost().equals(fileData.getPost());
//    }
//
//    @Override
//    public int hashCode() {
//        int result = Objects.hash(getFile_id(), getFileName(), getFileType(), getPost());
//        result = 31 * result + Arrays.hashCode(getFileContent());
//        return result;
//    }
}
