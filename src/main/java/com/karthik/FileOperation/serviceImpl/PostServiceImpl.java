package com.karthik.FileOperation.serviceImpl;

import com.karthik.FileOperation.entity.Post;
import com.karthik.FileOperation.exception.PostNotFoundException;
import com.karthik.FileOperation.repository.PostRepository;
import com.karthik.FileOperation.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Override
    public Post createComment(Post post) {

        post = postRepository.save(post.builder()
                .comment(post.getComment())
                .build());

        if (post != null) {
            return post;
        }
        return null;
    }

    @Override
    public Post updateComment(Long id, Post post) {

        if (postRepository.findById(id).isPresent()) {

            Post postData = postRepository.findById(id).get();
            postData.setComment(post.getComment());

            Post updatePostRecord = postRepository.save(postData);
            return updatePostRecord;
        } else {
            return null;
        }
    }

    @Override
    public List<Post> getAllPost() {
        List<Post> allPost = postRepository.findAll();

        if (allPost.isEmpty()) {
            throw new PostNotFoundException("No Post available");
        } else {
            return allPost;
        }
    }

    @Override
    public Post deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        if (post != null) {
            postRepository.delete(post);
            return post;
        }
        return null;
    }

    @Override
    public Optional<Post> findPostById(Long id) {

        Optional<Post> postData = Optional.ofNullable(postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id)));
        return postData;
    }

    @Override
    public void deleteById(long id) {
        postRepository.deleteById(id);
    }
}
