package com.ryan.lease.web.admin.controller.apartment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ryan.lease.common.result.Result;
import com.ryan.lease.common.result.ResultCodeEnum;
import com.ryan.lease.model.entity.FeeKey;
import com.ryan.lease.model.entity.FeeValue;
import com.ryan.lease.web.admin.service.FeeKeyService;
import com.ryan.lease.web.admin.service.FeeValueService;
import com.ryan.lease.web.admin.vo.fee.FeeKeyVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "房间杂费管理")
@RestController
@RequestMapping("/admin/fee")
public class FeeController {

    @Autowired
    private FeeKeyService feeKeyService;

    @Autowired
    private FeeValueService feeValueService;

    @Operation(summary = "保存或更新杂费名称")
    @PostMapping("key/saveOrUpdate")
    public Result saveOrUpdateFeeKey(@RequestBody FeeKey feeKey) {
        if (feeKey == null) {
            return Result.build("属性名称为空", ResultCodeEnum.PARAM_ERROR);
        }
        boolean result = feeKeyService.saveOrUpdate(feeKey);
        if (!result) {
            return Result.fail();
        }
        return Result.ok();
    }

    @Operation(summary = "保存或更新杂费值")
    @PostMapping("value/saveOrUpdate")
    public Result saveOrUpdateFeeValue(@RequestBody FeeValue feeValue) {
        boolean result = feeValueService.saveOrUpdate(feeValue);
        if (!result) {
            return Result.fail();
        }
        return Result.ok();
    }

    @Operation(summary = "查询全部杂费名称和杂费值列表")
    @GetMapping("list")
    public Result<List<FeeKeyVo>> feeInfoList() {
        List<FeeKeyVo> list = feeKeyService.feeInfoList();
        return Result.ok(list);
    }

    @Operation(summary = "根据id删除杂费名称")
    @DeleteMapping("key/deleteById")
    public Result deleteFeeKeyById(@RequestParam Long feeKeyId) {
        boolean result = feeKeyService.removeById(feeKeyId);
        if (!result) {
            return Result.fail();
        }
        LambdaQueryWrapper<FeeValue> wrapper = Wrappers.lambdaQuery(FeeValue.class).eq(FeeValue::getFeeKeyId, feeKeyId);
        boolean result1 = feeValueService.remove(wrapper);
        if (!result1) {
            return Result.fail();
        }
        return Result.ok();
    }

    @Operation(summary = "根据id删除杂费值")
    @DeleteMapping("value/deleteById")
    public Result deleteFeeValueById(@RequestParam Long id) {
        feeValueService.removeById(id);
        return Result.ok();
    }
}
