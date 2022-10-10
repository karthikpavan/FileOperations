package com.karthik.FileOperation.integration;

import com.karthik.FileOperation.entity.Post;
import com.karthik.FileOperation.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTests {

    private static final Logger logger = LoggerFactory.getLogger(PostControllerTests.class);

    @LocalServerPort
    private int port;

    private String baseURL = "http://localhost";
    @Autowired
    private static RestTemplate restTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    PostRepository postRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setup() {
        baseURL = baseURL.concat(":").concat(port + "").concat("/api/post/v1");
    }

    @Test
    @DisplayName("Save Post")
    public void testSavePost() {

        Post post = new Post(1l, "first comment");
        Post postResponse = restTemplate.postForObject(baseURL.concat("/savePost"), post, Post.class);
        Assertions.assertEquals("first comment", postResponse.getComment());
        Assertions.assertEquals(1, postRepository.findAll().size());

    }

    @Test
    @DisplayName("getPost")
    @Sql(statements = "insert into post(comment, post_id) values('second comment', 2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM POST WHERE POST_ID = 2", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetPosts() {

        List<Post> postList = restTemplate.getForObject(baseURL.concat("/posts"), List.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, postList.size()),
                () -> Assertions.assertEquals(1, postRepository.findAll().size())
        );
    }

    @Test
    @DisplayName("getPostById")
    @Sql(statements = "insert into post(comment, post_id) values('third comment', 3)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM POST WHERE POST_ID = 3", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetPostById() {

        Post postResponse = restTemplate.getForObject(baseURL.concat("/findPost/{id}"), Post.class, 3);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(postResponse),
                () -> Assertions.assertEquals(3, postResponse.getPost_id()),
                () -> Assertions.assertEquals("third comment", postResponse.getComment())
        );
    }

    @Test
    @DisplayName("Update Post")
    @Sql(statements = "insert into post(comment, post_id) values('fourth comment', 4)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM POST WHERE POST_ID = 4", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdatePost() {

//        updating fourth comment to fifth comment
        Post post = new Post(4l, "fifth comment");
        restTemplate.put(baseURL.concat("/update/{id}"), post, 4);
        Post postfromDB = postRepository.findById(4l).get();

        Assertions.assertAll(
                () -> Assertions.assertNotNull(postfromDB),
                () -> Assertions.assertEquals("fifth comment", postfromDB.getComment()),
                () -> Assertions.assertEquals(4, postfromDB.getPost_id())
        );
    }

    @Test
    @DisplayName("Delete Post by ID")
    @Sql(statements = "insert into post(comment, post_id) values('sixth comment', 6)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testDeleteByID() {

        Post postData = postRepository.findById(6l).get();
        Assertions.assertEquals(6, postData.getPost_id());
        postRepository.delete(postData);

        Post post1 = null;

        Optional<Post> optionalFileData = postRepository.findById(6l);
        if (optionalFileData.isPresent()) {
            post1 = optionalFileData.get();
        }
        assertThat(post1).isNull();
    }

}
