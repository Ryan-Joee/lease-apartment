package com.ryan.lease.web.admin.mapper;

import com.ryan.lease.model.entity.PaymentType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【payment_type(支付方式表)】的数据库操作Mapper
 * @createDate 2023-07-24 15:48:00
 * @Entity com.ryan.lease.model.PaymentType
 */
public interface PaymentTypeMapper extends BaseMapper<PaymentType> {

    /**
     * 根据房间id查询PaymentType
     * @param id 房间id
     * @return List<PaymentType>
     */
    List<PaymentType> selectListByRoomId(Long id);
}




