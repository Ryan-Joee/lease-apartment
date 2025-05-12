package com.ryan.lease.web.admin.controller.apartment;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ryan.lease.common.result.Result;
import com.ryan.lease.model.entity.FacilityInfo;
import com.ryan.lease.model.enums.ItemType;
import com.ryan.lease.web.admin.service.FacilityInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "配套管理")
@RestController
@RequestMapping("/admin/facility")
public class FacilityController {

    @Autowired
    private FacilityInfoService service;

    @Operation(summary = "[根据类型]查询配套信息列表")
    @GetMapping("list")
    public Result<List<FacilityInfo>> listFacility(@RequestParam(required = false) ItemType type) {
        LambdaQueryWrapper<FacilityInfo> list = Wrappers.lambdaQuery(FacilityInfo.class).eq(type != null, FacilityInfo::getType, type);
        List<FacilityInfo> facilityInfoList = service.list(list);
        return Result.ok(facilityInfoList);
    }

    @Operation(summary = "新增或修改配套信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody FacilityInfo facilityInfo) {
        boolean result = service.saveOrUpdate(facilityInfo);
        if (!result) {
            return Result.fail();
        }
        return Result.ok();
    }

    @Operation(summary = "根据id删除配套信息")
    @DeleteMapping("deleteById")
    public Result removeFacilityById(@RequestParam Long id) {
        boolean result = service.removeById(id);
        if (!result) {
            return Result.fail();
        }
        return Result.ok();
    }

}
