package com.miniBank.service.impl;

import com.miniBank.model.entity.Branch;
import com.miniBank.repository.BranchRepository;
import com.miniBank.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    @Override
    public List<Branch> getAllBranch() {
        return branchRepository.findAll();
    }

    @Override
    public Branch getBranchById(String id) {
        if (branchRepository.findById(id).isPresent()) {
            return branchRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found");
        }
    }

    @Override
    public Branch saveBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public Branch updateBranch(Branch branch) {
        return saveBranch(branch);
    }

    @Override
    public void deleteBranch(String id) {
        if(branchRepository.findById(id).isPresent()) {
            branchRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found");
        }
    }
}
