package com.usermgmt.clientSide.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateBranchDTO {
    private String id;

    private String name;

    private String type;

    private String address;

    private boolean flagActive;

    private String createdBy = "SYSTEM";

    public UpdateBranchDTO(String id, String name, String type, String address, boolean flagActive) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.address = address;
        this.flagActive = flagActive;
    }
}
