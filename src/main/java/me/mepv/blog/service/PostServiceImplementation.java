package me.mepv.blog.service;

import lombok.extern.slf4j.Slf4j;
import me.mepv.blog.dto.ApiResponse;
import me.mepv.blog.dto.PostDTO;
import me.mepv.blog.dto.PostResponseDTO;
import me.mepv.blog.dto.PostUpdateDTO;
import me.mepv.blog.entity.Post;
import me.mepv.blog.exception.NoPostException;
import me.mepv.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceImplementation implements PostService {

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    public PostServiceImplementation(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PostDTO> getBlogs() {
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()) {
            log.error("there is no post created yet");
            throw new NoPostException("There has been an error loading post");
        }

        return posts
                .stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO findPostByTitle(String title) {
        Optional<Post> optional = postRepository.findPostByTitle(title);
        if (optional.isPresent()) return modelMapper.map(optional.get(), PostDTO.class);
        else {
            log.error(String.format("findPostByTitle() // No post with title: %s", title));
            throw new NoPostException(String.format("No post with title: %s", title));
        }
    }

    @Override
    public PostResponseDTO savePost(PostDTO postDTO) {
        Post post = modelMapper.map(postDTO, Post.class);
        post.setCreatedAt(Instant.now());
        post.setUpdatedAt(Instant.now());
        postRepository.save(post);
        String URI = "/api/v1/post/";
        LocalDate publishDate = LocalDate.ofInstant(post.getCreatedAt(), ZoneOffset.UTC);

        return new PostResponseDTO(
                post.getTittle(),
                post.getContent(),
                post.getUsername(),
                publishDate,
                URI.concat(titleToURI(post.getTittle())),
                String.format("Post '%s' saved successfully.", post.getTittle())
        );
    }

    @Override
    public Post updatePost(PostUpdateDTO postDTO) {
        Post post = modelMapper.map(findPostByTitle(postDTO.getTittle()), Post.class);

        if (!post.getTittle().equals(postDTO.getNewTitle())) {
            post.setTittle(postDTO.getNewTitle());
        }
        post.setContent(postDTO.getNewContent());
        post.setUpdatedAt(Instant.now());

        return postRepository.save(post);
    }

    @Override
    public ApiResponse deletePost(String title) {
        Post post = modelMapper.map(findPostByTitle(title), Post.class);
        postRepository.deleteById(post.getId());

        return new ApiResponse(String.format("Post '%s' deleted successfully.", title));
    }

    private String titleToURI(String title) {
        String[] titleArray = title.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : titleArray) {
            stringBuilder.append(s).append("-");
        }
        String s = stringBuilder.toString();

        return Optional.of(s)
                .filter(str -> str.length() != 0)
                .map(str -> str.substring(0, str.length() - 1))
                .orElse(s);
    }
}
