package com.ryan.lease.web.admin.controller.apartment;


import com.ryan.lease.common.result.Result;
import com.ryan.lease.common.result.ResultCodeEnum;
import com.ryan.lease.model.entity.LeaseTerm;
import com.ryan.lease.web.admin.service.LeaseTermService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "租期管理")
@RequestMapping("/admin/term")
@RestController
public class LeaseTermController {

    @Autowired
    private LeaseTermService leaseTermService;

    @GetMapping("list")
    @Operation(summary = "查询全部租期列表")
    public Result<List<LeaseTerm>> listLeaseTerm() {
        List<LeaseTerm> list = leaseTermService.list();
        if (list.isEmpty()) {
            return Result.fail();
        }
        return Result.ok(list);
    }

    @PostMapping("saveOrUpdate")
    @Operation(summary = "保存或更新租期信息")
    public Result saveOrUpdate(@RequestBody LeaseTerm leaseTerm) {
        boolean result = leaseTermService.saveOrUpdate(leaseTerm);
        if (!result) {
            return Result.fail();
        }
        return Result.ok();
    }

    @DeleteMapping("deleteById")
    @Operation(summary = "根据ID删除租期")
    public Result deleteLeaseTermById(@RequestParam Long id) {
        boolean result = leaseTermService.removeById(id);
        if (!result) {
            return Result.fail();
        }
        return Result.ok();
    }
}
