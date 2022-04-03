package me.mepv.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateDTO {

    @NotBlank(message = "The tittle is required.")
    private String title;
    @JsonProperty(value = "new_title")
    private String newTitle;
    private String content;
}
