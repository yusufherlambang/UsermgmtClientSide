package com.usermgmt.clientSide.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MstBranchDTO {
    
    private String id;
    private String name;
    private String type;
    private String address;
    private boolean flagActive;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;

}
