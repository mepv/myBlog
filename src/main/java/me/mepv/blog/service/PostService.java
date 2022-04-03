package me.mepv.blog.service;

import me.mepv.blog.dto.ApiResponse;
import me.mepv.blog.dto.PostDTO;
import me.mepv.blog.dto.PostResponseDTO;
import me.mepv.blog.dto.PostUpdateDTO;

import java.util.Collection;

public interface PostService {

    Collection<PostDTO> getPosts();

    PostDTO findPostByTitle(String title);

    PostResponseDTO savePost(PostDTO post);

    ApiResponse updatePost(PostUpdateDTO post);

    ApiResponse deletePost(String title);
}
