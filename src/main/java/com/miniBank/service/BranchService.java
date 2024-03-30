package com.miniBank.service;

import com.miniBank.model.entity.Branch;

import java.util.List;

public interface BranchService {
    List<Branch> getAllBranch();
    Branch getBranchById(String id);
    Branch saveBranch(Branch branch);
    Branch updateBranch(Branch branch);
    void deleteBranch(String id);
}
