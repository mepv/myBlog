package me.mepv.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateDTO {

    @NotBlank
    private String tittle;
    @NotBlank
    @JsonProperty(value = "new_title")
    private String newTitle;
    @NotEmpty
    @JsonProperty(value = "new_content")
    private String newContent;
    @NotBlank
    private String username;
}
