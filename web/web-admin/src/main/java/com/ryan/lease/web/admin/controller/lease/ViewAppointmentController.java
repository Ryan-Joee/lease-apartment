package com.ryan.lease.web.admin.controller.lease;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryan.lease.common.result.Result;
import com.ryan.lease.model.entity.ViewAppointment;
import com.ryan.lease.model.enums.AppointmentStatus;
import com.ryan.lease.web.admin.service.ViewAppointmentService;
import com.ryan.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.ryan.lease.web.admin.vo.appointment.AppointmentVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "预约看房管理")
@RequestMapping("/admin/appointment")
@RestController
public class ViewAppointmentController {

    @Autowired
    private ViewAppointmentService service;

    @Operation(summary = "分页查询预约信息")
    @GetMapping("page")
    public Result<IPage<AppointmentVo>> page(@RequestParam long current, @RequestParam long size, AppointmentQueryVo queryVo) {
        Page<AppointmentVo> page = new Page<>(current, size);
        IPage<AppointmentVo> result = service.pageAppointment(page, queryVo);
        return Result.ok(result);
    }

    @Operation(summary = "根据id更新预约状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam AppointmentStatus status) {
        LambdaUpdateWrapper<ViewAppointment> updateWrapper = Wrappers.lambdaUpdate(ViewAppointment.class)
                .eq(ViewAppointment::getId, id)
                .set(ViewAppointment::getAppointmentStatus, status);
        service.update(updateWrapper);
        return Result.ok();
    }

}
