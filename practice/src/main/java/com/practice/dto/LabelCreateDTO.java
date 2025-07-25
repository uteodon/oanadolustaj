package com.practice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabelCreateDTO {

    @NotBlank String name;
    @NotBlank String description;
    @NotBlank String filePath;
}
