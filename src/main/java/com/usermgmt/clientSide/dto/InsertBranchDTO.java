package com.usermgmt.clientSide.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class InsertBranchDTO {
    private String id;

    private String name;

    private String type;

    private String address;

    private boolean flagActive;

    private String createdBy = "SYSTEM";
}
