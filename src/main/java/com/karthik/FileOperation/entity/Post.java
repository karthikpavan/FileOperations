package com.karthik.FileOperation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "post")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long post_id;

    private String comment;

//    public Post() {
//
//    }
//
//    public Post(long post_id, String comment) {
//        this.post_id = post_id;
//        this.comment = comment;
//    }
//
//    public long getPost_id() {
//        return post_id;
//    }
//
//    public void setPost_id(long post_id) {
//        this.post_id = post_id;
//    }
//
//    public String getComment() {
//        return comment;
//    }
//
//    public void setComment(String comment) {
//        this.comment = comment;
//    }
//
//    @Override
//    public String toString() {
//        return "Post{" +
//                "post_id=" + post_id +
//                ", comment='" + comment + '\'' +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Post post = (Post) o;
//        return post_id == post.post_id && comment.equals(post.comment);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(post_id, comment);
//    }
}
