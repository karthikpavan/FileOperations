package com.karthik.FileOperation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karthik.FileOperation.entity.Post;
import com.karthik.FileOperation.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(PostControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldSavePost() throws Exception {

        Post post = new Post(1l, "first comment");

        Mockito.when(postRepository.save(Mockito.any(Post.class))).thenReturn(new Post());
        
        mockMvc.perform(post("/api/post/v1/savePost")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    public void shouldReturnPost() throws Exception {

        Post post = new Post(10L, "returning post based in id");

        when(postRepository.findById(10l)).thenReturn(Optional.of(post));
        mockMvc.perform(get("/api/post/v1/findPost/{id}", 10l))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.post_id").value(10l))
                .andExpect(jsonPath("$.comment").value(post.getComment()))
                .andDo(print());
    }

    @Test
    public void shouldReturnPostNotFound() throws Exception {

        Mockito.when(postRepository.findById(1l)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/post/v1/findPost/{id}", 1l))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void shouldReturnListofPost() throws Exception {

        List<Post> postDataList = new ArrayList<>(
                Arrays.asList(
                        Post.builder().post_id(1l).comment("first list comment").build(),
                        Post.builder().post_id(2l).comment("second list comment").build(),
                        Post.builder().post_id(3l).comment("third list comment").build()
                ));

        Mockito.when(postRepository.findAll()).thenReturn(postDataList);
        mockMvc.perform(get("/api/post/v1/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(postDataList.size()))
                .andDo(print());
    }

    @Test
    public void shouldUpdatePost() throws Exception {

        Post postData = Post.builder().post_id(1l).comment("first list comment").build();
        Post updatedPostData = Post.builder().post_id(1l).comment("first list comment -to- second comment").build();

        when(postRepository.findById(1l)).thenReturn(Optional.of(postData));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPostData);

        mockMvc.perform(put("/api/post/v1/update/{id}", 1l).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPostData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.post_id").value(1l))
                .andExpect(jsonPath("$.comment").value(updatedPostData.getComment()))
                .andDo(print());
    }

    @Test
    public void shouldReturnnotFoundUpdatedPost() throws Exception {

        Post updatedpostData = Post.builder().post_id(100l).comment("updated post").build();

        when(postRepository.findById(100l)).thenReturn(Optional.empty());
        when(postRepository.save(any(Post.class))).thenReturn(updatedpostData);

        mockMvc.perform(put("/api/post/v1/update/{id}", 100l).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedpostData)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldDeletePost() throws Exception {

        doNothing().when(postRepository).deleteById(20l);
        mockMvc.perform(delete("/api/post/v1/delete/{id}", 20l))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}
