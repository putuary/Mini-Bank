package com.miniBank.controller;

import com.miniBank.model.entity.Branch;
import com.miniBank.model.response.CommonResponse;
import com.miniBank.service.BranchService;
import com.miniBank.utils.constant.ApiPathConstant;
import com.miniBank.utils.constant.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION + ApiPathConstant.BRANCH)
@PreAuthorize("hasAuthority('ADMIN')")
public class BranchController {
    private final BranchService branchService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<Branch>>> getAllBranch () {
        List<Branch> branches= branchService.getAllBranch();
        String message =String.format(Constant.MESSAGE_SUCCESS_GET_ALL, "Branch");
        CommonResponse<List<Branch>> response=new CommonResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(branches);
        response.setMessage(message);
        return ResponseEntity.status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<Branch>> getBranchById (@PathVariable String id) {
        Branch branch= branchService.getBranchById(id);
        String message =String.format(Constant.MESSAGE_SUCCESS_GET_ID, "Branch", id);
        CommonResponse<Branch> response=new CommonResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(branch);
        response.setMessage(message);
        return ResponseEntity.status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping()
    public ResponseEntity<CommonResponse<Branch>> saveBranch (@RequestBody Branch branch) {
        Branch result = branchService.saveBranch(branch);
        String message =String.format(Constant.MESSAGE_SUCCESS_INSERT, "Branch");
        CommonResponse<Branch> response=new CommonResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setData(result);
        response.setMessage(message);
        return ResponseEntity.status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
