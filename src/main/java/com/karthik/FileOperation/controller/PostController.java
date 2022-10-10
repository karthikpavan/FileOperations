package com.karthik.FileOperation.controller;

import com.karthik.FileOperation.entity.Post;
import com.karthik.FileOperation.serviceImpl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/post/v1")
public class PostController {

    @Autowired
    PostServiceImpl postService;

    @PostMapping("/savePost")
    public ResponseEntity<?> savePost(@RequestBody Post post) throws IOException {

        Post postStatus = postService.createComment(post);

        if (postStatus == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Post didn't created!");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(postStatus);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") Long id, @RequestBody Post post) {

        Post postDataReceived = postService.updateComment(id, post);

        if (postDataReceived == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Data didn't updated successfully!!");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(postDataReceived);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllFiles() {
        List<Post> postList = postService.getAllPost();
        if (postList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No Post found!!");
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(postList);
        }
    }

    @GetMapping("/findPost/{id}")
    public ResponseEntity<?> findPostById(@PathVariable Long id) {
        Optional<Post> fileReceived = postService.findPostById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(fileReceived);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        Post deleteResponse = postService.deletePost(id);

        if (deleteResponse == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No Data found to delete for ID :" + id);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body("Post Delete Successfully.. for ID :" + id);
    }

}
