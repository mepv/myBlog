package me.mepv.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {

    private String tittle;
    private String content;
    @JsonProperty(value = "author_name")
    private String authorName;
    @JsonProperty(value = "date_published")
    private LocalDate publishDate;
    private String URI;
    private String message;
}
