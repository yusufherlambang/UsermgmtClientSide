package com.usermgmt.clientSide.service;

import com.usermgmt.clientSide.dto.InsertBranchDTO;
import com.usermgmt.clientSide.dto.MstBranchDTO;
import com.usermgmt.clientSide.dto.UpdateBranchDTO;
import org.springframework.data.domain.Page;

public interface MstBranchService {
    MstBranchDTO getById(String id);


    Page<MstBranchDTO> getAllBranch(Integer page, String id, String name, String type, String address, String createdBy, String updatedBy);

    UpdateBranchDTO getBranchToUpdate(String id);

    InsertBranchDTO insertBranch(InsertBranchDTO insertDTO);

    void updateBranch(UpdateBranchDTO updateBranchDTO, String id);

    void deleteById(String id);
}
