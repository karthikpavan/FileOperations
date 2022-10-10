package com.karthik.FileOperation.repository;

import com.karthik.FileOperation.entity.FileData;
import com.karthik.FileOperation.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
