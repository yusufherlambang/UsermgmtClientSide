package com.usermgmt.clientSide.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usermgmt.clientSide.ErrorResponse;
import com.usermgmt.clientSide.dto.InsertBranchDTO;
import com.usermgmt.clientSide.dto.MstBranchDTO;
import com.usermgmt.clientSide.dto.UpdateBranchDTO;
import com.usermgmt.clientSide.helper.Dropdown;
import com.usermgmt.clientSide.service.MstBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.LinkedHashMap;


@Controller
@RequestMapping("/branch")
public class MstBranchController {

    @Autowired
    private MstBranchService mstBranchService;

    @GetMapping("/list")
    public String branchIndex(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "") String id,
                               @RequestParam(defaultValue = "") String name,
                               @RequestParam(defaultValue = "") String type,
                               @RequestParam(defaultValue = "") String address,
                               @RequestParam(defaultValue = "") String createdBy,
                               @RequestParam(defaultValue = "") String updatedBy,
                               Model model){

        try {
            var gridBranch = mstBranchService.getAllBranch(page, id, name, type, address, createdBy, updatedBy);

            System.out.println("ini gridBranch ctrl = "+ gridBranch.getContent());

            model.addAttribute("gridBranch",gridBranch.getContent());
            model.addAttribute("currentPage",page);
            model.addAttribute("totalPages",gridBranch.getTotalPages());
            model.addAttribute("breadCrumbs","All Branch");
            model.addAttribute("id",id);
            model.addAttribute("name",name);
            model.addAttribute("type",type);
            model.addAttribute("address",address);
            model.addAttribute("createdBy",createdBy);
            model.addAttribute("updatedBy",updatedBy);

            return "branch/branch-index";

        }catch (Exception e){
            String errors = e.getMessage();

            model.addAttribute("breadCrumbs","Internal Server Error");
            model.addAttribute("message", errors);
            return "branch/server-error";
        }
    }

    @GetMapping("/{id}")
    public String getBranchById(@PathVariable("id") String id,
                                Model model){
        System.out.println("id = "+id);
        try {

            MstBranchDTO branch = mstBranchService.getById(id);
            model.addAttribute("branch",branch);
            model.addAttribute("breadCrumbs","Branch Detail");

            return "branch/branch-by-id";
        }catch (HttpClientErrorException e){

            String strErrors = e.getResponseBodyAsString();
            System.out.println("strErrors = "+ strErrors);

            model.addAttribute("breadCrumbs","Id Not Found");
            model.addAttribute("type", "notFound");
            model.addAttribute("message", strErrors);
            return "branch/not-found";

        } catch (Exception e){

            String errors = e.getMessage();

            model.addAttribute("breadCrumbs","Internal Server Error");
            model.addAttribute("message", errors);
            return "branch/server-error";
        }
    }

    @GetMapping("/upsertForm")
    public String BranchUpsertForm(@RequestParam(required = false) String id,
                                   Model model){

        try {
            model.addAttribute("typeDropdown", Dropdown.getType());

            if(id != null){
                UpdateBranchDTO branchDTO = mstBranchService.getBranchToUpdate(id);

                model.addAttribute("branch", branchDTO);
                model.addAttribute("type","update");
                model.addAttribute("breadCrumbs","Branch Index / Update Branch");
            } else {
                InsertBranchDTO branchDTO = new InsertBranchDTO();

                model.addAttribute("branch", branchDTO);
                model.addAttribute("type","insert");
                model.addAttribute("breadCrumbs","Branch Index / Insert Branch");
            }

            return "branch/upsert-form";
        }catch (HttpClientErrorException e){

            String strErrors = e.getResponseBodyAsString();
            System.out.println("strErrors = "+ strErrors);

            model.addAttribute("breadCrumbs","Id Not Found");
            model.addAttribute("type", "notFound");
            model.addAttribute("message", strErrors);
            return "branch/not-found";
        }catch (Exception e){
            String errors = e.getMessage();

            model.addAttribute("breadCrumbs","Internal Server Error");
            model.addAttribute("message", errors);
            return "branch/server-error";
        }

    }

    @PostMapping("/insert")
    public String insertBranch(@ModelAttribute("branch") InsertBranchDTO insertDTO,
                             Model model) throws JsonProcessingException {

        try {
            mstBranchService.insertBranch(insertDTO);

            return "redirect:/branch/list";

        }catch (HttpClientErrorException errors){

            String strErrors = errors.getResponseBodyAsString();

            ErrorResponse errorResponse = new ObjectMapper().readValue(strErrors, ErrorResponse.class);

            LinkedHashMap<String, String> errHash = (LinkedHashMap<String, String>) errorResponse.getMessage();
            System.out.println("err key = "+errHash.keySet());

            model.addAttribute("errId", errHash.get("id"));
            model.addAttribute("errAddress", errHash.get("address"));
            model.addAttribute("errName", errHash.get("name"));
            model.addAttribute("typeDropdown", Dropdown.getType());
            model.addAttribute("type","insert");
            model.addAttribute("breadCrumbs","Branch / Insert Branch");

            return "branch/upsert-form";
        }catch (Exception e){
            String errors = e.getMessage();

            model.addAttribute("breadCrumbs","Internal Server Error");
            model.addAttribute("message", errors);
            return "branch/server-error";
        }
    }

    @PostMapping("/update")
    public String updateBranch(@ModelAttribute("branch") UpdateBranchDTO updateBranchDTO,
                             Model model) throws JsonProcessingException {

        try {
            mstBranchService.updateBranch(updateBranchDTO, updateBranchDTO.getId());

            return "redirect:/branch/list";

        }catch (HttpClientErrorException errors){

            String strErrors = errors.getResponseBodyAsString();

            ErrorResponse errorResponse = new ObjectMapper().readValue(strErrors, ErrorResponse.class);

            LinkedHashMap<String, String> errHash = (LinkedHashMap<String, String>) errorResponse.getMessage();
            System.out.println("err key = "+errHash.keySet());

            model.addAttribute("errAddress", errHash.get("address"));
            model.addAttribute("errName", errHash.get("name"));
            model.addAttribute("typeDropdown", Dropdown.getType());
            model.addAttribute("type","update");
            model.addAttribute("breadCrumbs","Branch / Update Branch");

            return "branch/upsert-form";
        }catch (Exception e){
            String errors = e.getMessage();

            model.addAttribute("breadCrumbs","Internal Server Error");
            model.addAttribute("message", errors);
            return "branch/server-error";
        }
    }

    @GetMapping("/delete")
    public String deleteBranchById(@RequestParam(required = true) String id,
                                   Model model){

        try {
            mstBranchService.deleteById(id);

            return "redirect:/branch/list";
        }catch (HttpClientErrorException e){

            String strErrors = e.getResponseBodyAsString();
            System.out.println("strErrors = "+ strErrors);

            model.addAttribute("breadCrumbs","Id Not Found");
            model.addAttribute("type", "notFound");
            model.addAttribute("message", strErrors);
            return "branch/not-found";

        } catch (Exception e){
            String errors = e.getMessage();

            model.addAttribute("breadCrumbs","Internal Server Error");
            model.addAttribute("message", errors);
            return "branch/server-error";
        }
    }
}
