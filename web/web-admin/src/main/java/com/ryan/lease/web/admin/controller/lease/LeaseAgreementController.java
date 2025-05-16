package com.ryan.lease.web.admin.controller.lease;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryan.lease.common.result.Result;
import com.ryan.lease.model.entity.LeaseAgreement;
import com.ryan.lease.model.enums.LeaseStatus;
import com.ryan.lease.web.admin.service.LeaseAgreementService;
import com.ryan.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.ryan.lease.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "租约管理")
@RestController
@RequestMapping("/admin/agreement")
public class LeaseAgreementController {

    @Autowired
    private LeaseAgreementService service;

    @Operation(summary = "保存或修改租约信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody LeaseAgreement leaseAgreement) {
        service.saveOrUpdate(leaseAgreement);
        return Result.ok();
    }

    @Operation(summary = "根据条件分页查询租约列表")
    @GetMapping("page")
    public Result<IPage<AgreementVo>> page(@RequestParam long current, @RequestParam long size, AgreementQueryVo queryVo) {
        Page<AgreementVo> page = new Page<>(current, size);
        IPage<AgreementVo> result = service.pageAgreement(page, queryVo);
        return Result.ok(result);
    }

    @Operation(summary = "根据id查询租约信息")
    @GetMapping(name = "getById")
    public Result<AgreementVo> getById(@RequestParam Long id) {
        AgreementVo result = service.getAgreementById(id);
        return Result.ok(result);
    }

    @Operation(summary = "根据id删除租约信息")
    @DeleteMapping("removeById")
    public Result removeById(@RequestParam Long id) {
        // 因为删除只设计删除租约信息，所以可以直接使用通用service来进行删除
        // 不能因为删除了租约信息，就把公寓信息，房间信息等也一起删除了
        service.removeById(id);
        return Result.ok();
    }

    @Operation(summary = "根据id更新租约状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam LeaseStatus status) {
        LambdaUpdateWrapper<LeaseAgreement> updateWrapper = Wrappers.lambdaUpdate(LeaseAgreement.class)
                .eq(LeaseAgreement::getId, id)
                .set(LeaseAgreement::getStatus, status);
        service.update(updateWrapper);
        return Result.ok();
    }

}

