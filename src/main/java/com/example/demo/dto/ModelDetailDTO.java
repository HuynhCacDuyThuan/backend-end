package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelDetailDTO {
    private Long modelId;
    private String name;
    private boolean activeFlag;
    private boolean deleteFlag;
    private String createdBy;
    private boolean block;  // Thêm trường block
}
