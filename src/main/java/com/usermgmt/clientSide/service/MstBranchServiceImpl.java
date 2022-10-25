package com.usermgmt.clientSide.service;

import com.usermgmt.clientSide.dto.InsertBranchDTO;
import com.usermgmt.clientSide.dto.MstBranchDTO;
import com.usermgmt.clientSide.dto.UpdateBranchDTO;
import com.usermgmt.clientSide.helper.RestPageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class MstBranchServiceImpl implements MstBranchService{

    @Autowired
    private RestTemplate restTemplate;

    private String url = "http://localhost:8080/usermgmt/v1/branch";

    @Override
    public MstBranchDTO getById(String id) {

        MstBranchDTO branch = restTemplate.getForObject(url+"/"+id, MstBranchDTO.class);

        System.out.println("ini branch "+branch);
        return branch;
    }

    @Override
    public Page<MstBranchDTO> getAllBranch(Integer page, String id, String name, String type, String address, String createdBy, String updatedBy) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url + "/list")
                .queryParam("page", page)
                .queryParam("id", id)
                .queryParam("name", name)
                .queryParam("type", type)
                .queryParam("address", address)
                .queryParam("createdBy", createdBy)
                .queryParam("updatedBy", updatedBy)
                .encode()
                .toUriString();

        ResponseEntity<RestPageImpl<MstBranchDTO>> allBranch = restTemplate.exchange(urlTemplate, HttpMethod.GET, null, new ParameterizedTypeReference<RestPageImpl<MstBranchDTO>>() {});

        Page<MstBranchDTO> response = allBranch.getBody();
        return response;
    }

    @Override
    public UpdateBranchDTO getBranchToUpdate(String id) {
        MstBranchDTO branchTemp = getById(id);

        UpdateBranchDTO updateBranchDTO = new UpdateBranchDTO(
                branchTemp.getId(),
                branchTemp.getName(),
                branchTemp.getType(),
                branchTemp.getAddress(),
                branchTemp.isFlagActive()
        );

        return updateBranchDTO;
    }

    @Override
    public InsertBranchDTO insertBranch(InsertBranchDTO insertDTO) {
        InsertBranchDTO branch = restTemplate.postForObject(url+"/add", insertDTO, InsertBranchDTO.class);

        System.out.println("ini new brand = "+branch);

        return branch;
    }

    @Override
    public void  updateBranch(UpdateBranchDTO updateBranchDTO, String id) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url + "/update")
                .queryParam("id", id)
                .encode()
                .toUriString();

        restTemplate.exchange(urlTemplate, HttpMethod.PUT, new HttpEntity<>(updateBranchDTO), void.class);
    }

    @Override
    public void deleteById(String id) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url + "/delete")
                .queryParam("id", id)
                .encode()
                .toUriString();

        restTemplate.delete(urlTemplate);
    }
}
