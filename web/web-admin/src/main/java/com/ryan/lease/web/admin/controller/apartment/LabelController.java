package com.ryan.lease.web.admin.controller.apartment;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ryan.lease.common.result.Result;
import com.ryan.lease.model.entity.LabelInfo;
import com.ryan.lease.model.enums.ItemType;
import com.ryan.lease.web.admin.service.LabelInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "标签管理")
@RestController
@RequestMapping("/admin/label")
public class LabelController {

    @Autowired
    private LabelInfoService service;

    @Operation(summary = "（根据类型）查询标签列表")
    @GetMapping("list")
    // 这个@RequestParam(required = false) ItemType type枚举类型会传给StringToBaseEnumConverterFactory
    // 之后通过StringToBaseEnumConverterFactory获取到对应的Converter
    // 从而使得WebDataBinder能够通过获取到的Converter实现枚举类型转换
    // 因为前端传过来的类型是type = 1/2/...的方式，传到后端需要通过WebDataBinder组件实现
    // 将前端传来的类型转为后端写好的枚举类型
    public Result<List<LabelInfo>> labelList(@RequestParam(required = false) ItemType type) {
        // 因为type不是必须从前端传过来的
        // 所以需要构造一个条件构造器来进行查询
/*        LambdaQueryWrapper<LabelInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(type != null, LabelInfo::getType, type);*/
        LambdaQueryWrapper<LabelInfo> wrapper = Wrappers.lambdaQuery(LabelInfo.class).eq(type != null, LabelInfo::getType, type);

        // 将查询条件放入list()方法中.
        List<LabelInfo> labelInfoList = service.list(wrapper);
        if (labelInfoList.isEmpty()) {
            return Result.fail();
        }
        return Result.ok(labelInfoList);
    }

    @Operation(summary = "新增或修改标签信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdateLabel(@RequestBody LabelInfo labelInfo) {

        return Result.ok();
    }

    @Operation(summary = "根据id删除标签信息")
    @DeleteMapping("deleteById")
    public Result deleteLabelById(@RequestParam Long id) {
        return Result.ok();
    }
}
