package com.karthik.FileOperation.service;

import com.karthik.FileOperation.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PostService {

    public Post createComment(Post post);

    public Post updateComment(Long id, Post post);

    public List<Post> getAllPost();

    public Post deletePost(Long id);

    Optional<Post> findPostById(Long id);

    void deleteById(long id);
}
