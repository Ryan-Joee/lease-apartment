package com.ryan.lease.web.admin.controller.apartment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ryan.lease.common.result.Result;
import com.ryan.lease.common.result.ResultCodeEnum;
import com.ryan.lease.model.entity.AttrKey;
import com.ryan.lease.model.entity.AttrValue;
import com.ryan.lease.web.admin.service.AttrKeyService;
import com.ryan.lease.web.admin.service.AttrValueService;
import com.ryan.lease.web.admin.vo.attr.AttrKeyVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "房间属性管理")
@RestController
@RequestMapping("/admin/attr")
public class AttrController {

    @Autowired
    private AttrKeyService attrKeyService;

    @Autowired
    private AttrValueService attrValueService;
    @Operation(summary = "新增或更新属性名称")
    @PostMapping("key/saveOrUpdate")
    public Result saveOrUpdateAttrKey(@RequestBody AttrKey attrKey) {
        boolean result = attrKeyService.saveOrUpdate(attrKey);
        if (!result) {
            return Result.fail();
        }
        return Result.ok();
    }

    @Operation(summary = "新增或更新属性值")
    @PostMapping("value/saveOrUpdate")
    public Result saveOrUpdateAttrValue(@RequestBody AttrValue attrValue) {
        boolean result = attrValueService.saveOrUpdate(attrValue);
        if (!result) {
            return Result.fail();
        }
        return Result.ok();
    }


    @Operation(summary = "查询全部属性名称和属性值列表")
    @GetMapping("list")
    public Result<List<AttrKeyVo>> listAttrInfo() {
        List<AttrKeyVo> list = attrKeyService.listAttrInfo();
        return Result.ok(list);
    }

    @Operation(summary = "根据id删除属性名称")
    @DeleteMapping("key/deleteById")
    public Result removeAttrKeyById(@RequestParam Long attrKeyId) {
        if (attrKeyId == null || attrKeyId <= 0) {
            return Result.build("属性ID无效", ResultCodeEnum.PARAM_ERROR);
        }
        // 删除属性名称
        boolean result = attrKeyService.removeById(attrKeyId);
        if (!result) {
            return Result.fail();
        }
        // 删除属性值
        LambdaQueryWrapper<AttrValue> wrapper = Wrappers.lambdaQuery(AttrValue.class).eq(AttrValue::getAttrKeyId, attrKeyId);
        result = attrValueService.remove(wrapper);
        if (!result) {
            return Result.fail();
        }
        return Result.ok();
    }

    @Operation(summary = "根据id删除属性值")
    @DeleteMapping("value/deleteById")
    public Result removeAttrValueById(@RequestParam Long id) {
        if (id == null || id <= 0) {
            return Result.build("属性ID无效", ResultCodeEnum.PARAM_ERROR);
        }
        boolean result = attrValueService.removeById(id);
        if (!result) {
            return Result.fail();
        }
        return Result.ok();
    }

}
