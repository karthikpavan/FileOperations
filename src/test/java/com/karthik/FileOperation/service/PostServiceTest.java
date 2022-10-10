package com.karthik.FileOperation.service;

import com.karthik.FileOperation.entity.FileData;
import com.karthik.FileOperation.entity.Post;
import com.karthik.FileOperation.repository.PostRepository;
import com.karthik.FileOperation.serviceImpl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceTest.class);

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostServiceImpl postService;

    private Post postdata;

    @BeforeEach
    public void setup() throws IOException {
        logger.info("---> Sample Data preparation --->");

        postdata = Post.builder().post_id(55l).comment("Sample comment for post data").build();

        logger.info("Data : "+postdata.getPost_id()+", "+postdata.getComment());

        logger.info("<--- Sample Data preparation <---");
    }

    @Test
    public void savePost() throws IOException {

//        given
        when(postRepository.save(any(Post.class))).thenReturn(postdata);

//        when
        Post savedPost = postService.createComment(postdata);

//        then
        assertThat(savedPost).isNotNull();
    }

    @Test
    public void getPostList() throws IOException {

        // given
        Post postData1 = Post.builder().post_id(55l).comment("Sample next comment on post data").build();

        given(postRepository.findAll()).willReturn(Arrays.asList(postdata, postData1));

        // when
        List<Post> postDataList = postService.getAllPost();

        // then
        assertThat(postDataList).isNotNull();
        assertThat(postDataList.size()).isEqualTo(2);
    }

    @Test
    public void getPostById() {
        // given
        given(postRepository.findById(55L)).willReturn(Optional.of(postdata));

        // when
        Post postId = postService.findPostById(postdata.getPost_id()).get();

        // then
        assertThat(postId).isNotNull();
    }


//    @Test
//    public void updatePost(){
//        // given - precondition or setup
//        given(postRepository.save(postdata)).willReturn(postdata);
//        postdata.setComment("Updating comment in updatePost test function");
//
//        // when -  action or the be haviour that we are going test
//        Post updatedPost = postService.updateComment(postdata.getPost_id(), postdata);
//
//        // then - verify the output
//        assertThat(updatedPost.getComment()).isEqualTo("Updating comment in updatePost test function");
//
//    }

    @Test
    public void deletePostById() {
//        given
        doNothing().when(postRepository).deleteById(55l);

//        when
        postService.deleteById(55l);

//        then
        verify(postRepository, times(1)).deleteById(55l);
    }
}
