package com.ryan.lease.web.admin.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryan.lease.common.result.Result;
import com.ryan.lease.model.entity.UserInfo;
import com.ryan.lease.model.enums.BaseStatus;
import com.ryan.lease.web.admin.service.UserInfoService;
import com.ryan.lease.web.admin.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户信息管理")
@RestController
@RequestMapping("/admin/user")
public class UserInfoController {

    @Autowired
    private UserInfoService service;

    @Operation(summary = "分页查询用户信息")
    @GetMapping("page")
    public Result<IPage<UserInfo>> pageUserInfo(@RequestParam long current, @RequestParam long size, UserInfoQueryVo queryVo) {
        Page<UserInfo> page = new Page<>(current, size);
        LambdaQueryWrapper<UserInfo> queryWrapper = Wrappers.lambdaQuery(UserInfo.class)
                .like(queryVo.getPhone() != null, UserInfo::getPhone, queryVo.getPhone())
                .eq(queryVo.getStatus() != null, UserInfo::getStatus, queryVo.getStatus());
        Page<UserInfo> result = service.page(page, queryWrapper);
        return Result.ok(result);
    }

    @Operation(summary = "根据用户id更新账号状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam BaseStatus status) {
        LambdaUpdateWrapper<UserInfo> updateWrapper = Wrappers.lambdaUpdate(UserInfo.class)
                .eq(UserInfo::getId, id)
                .set(UserInfo::getStatus, status);
        service.update(updateWrapper);
        return Result.ok();
    }
}
