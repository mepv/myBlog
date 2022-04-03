package me.mepv.blog.controller;

import me.mepv.blog.dto.ApiResponse;
import me.mepv.blog.dto.PostDTO;
import me.mepv.blog.dto.PostResponseDTO;
import me.mepv.blog.dto.PostUpdateDTO;
import me.mepv.blog.service.PostServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {

    private final PostServiceImplementation postServiceImplementation;

    public PostController(PostServiceImplementation postServiceImplementation) {
        this.postServiceImplementation = postServiceImplementation;
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getPosts() {
        return new ResponseEntity<>(postServiceImplementation.getPosts(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> savePost(@RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postServiceImplementation.savePost(postDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updatePost(@Valid @RequestBody PostUpdateDTO postDTO) {
        return new ResponseEntity<>(postServiceImplementation.updatePost(postDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deletePost(@RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postServiceImplementation.deletePost(postDTO.getTitle()), HttpStatus.OK);
    }
}
