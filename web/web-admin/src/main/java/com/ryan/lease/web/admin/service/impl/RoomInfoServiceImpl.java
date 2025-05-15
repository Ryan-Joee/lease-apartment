package com.ryan.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ryan.lease.model.entity.*;
import com.ryan.lease.model.enums.ItemType;
import com.ryan.lease.web.admin.mapper.RoomInfoMapper;
import com.ryan.lease.web.admin.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryan.lease.web.admin.vo.graph.GraphVo;
import com.ryan.lease.web.admin.vo.room.RoomSubmitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {

    @Autowired
    private GraphInfoService graphInfoService;

    @Autowired
    private RoomAttrValueService roomAttrValueService;

    @Autowired
    private RoomFacilityService roomFacilityService;

    @Autowired
    private RoomLabelService roomLabelService;

    @Autowired
    private RoomPaymentTypeService roomPaymentTypeService;

    @Autowired
    private RoomLeaseTermService roomLeaseTermService;

    /**
     * 保存或更新房间信息
     * @param roomSubmitVo 房间详细信息
     */
    @Override
    public void saveOrUpdateRoom(RoomSubmitVo roomSubmitVo) {
        boolean isUpdate = roomSubmitVo.getId() != null;
        super.saveOrUpdate(roomSubmitVo);

        // 更新房间信息的逻辑：先删除原有的信息，再插入新信息
        if (isUpdate) {
            // 1. 删除图片列表
            LambdaQueryWrapper<GraphInfo> graphInfoWrapper = Wrappers.lambdaQuery(GraphInfo.class)
                    .eq(GraphInfo::getItemType, ItemType.ROOM)
                    .eq(GraphInfo::getItemId, roomSubmitVo.getId());
            graphInfoService.remove(graphInfoWrapper);

            // 2. 删除属性信息列表
            LambdaQueryWrapper<RoomAttrValue> attrWrapper = Wrappers.lambdaQuery(RoomAttrValue.class)
                    .eq(RoomAttrValue::getRoomId, roomSubmitVo.getId());
            roomAttrValueService.remove(attrWrapper);

            // 3. 删除配套信息列表
            LambdaQueryWrapper<RoomFacility> facilityWrapper = Wrappers.lambdaQuery(RoomFacility.class)
                    .eq(RoomFacility::getRoomId, roomSubmitVo.getId());
            roomFacilityService.remove(facilityWrapper);

            // 4. 删除标签信息列表
            LambdaQueryWrapper<RoomLabel> labelWrapper = Wrappers.lambdaQuery(RoomLabel.class)
                    .eq(RoomLabel::getRoomId, roomSubmitVo.getId());
            roomLabelService.remove(labelWrapper);

            // 5. 删除支付方式列表
            LambdaQueryWrapper<RoomPaymentType> paymentTypeWrapper = Wrappers.lambdaQuery(RoomPaymentType.class)
                    .eq(RoomPaymentType::getRoomId, roomSubmitVo.getId());
            roomPaymentTypeService.remove(paymentTypeWrapper);

            // 6. 删除可选租期列表
            LambdaQueryWrapper<RoomLeaseTerm> leaseTermWrapper = Wrappers.lambdaQuery(RoomLeaseTerm.class)
                    .eq(RoomLeaseTerm::getRoomId, roomSubmitVo.getId());
            roomLeaseTermService.remove(leaseTermWrapper);
        }

        // 1. 插入图片列表
        List<GraphVo> graphVoList = roomSubmitVo.getGraphVoList();
        if (!CollectionUtils.isEmpty(graphVoList)) {
            List<GraphInfo> graphInfoList = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                // 图片信息的id == 前端传过来的房间id ==> 表示这个图片属于哪个房间
                graphInfo.setId(roomSubmitVo.getId());
                graphInfo.setItemType(ItemType.ROOM);
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfoList.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfoList);
        }

        // 2. 插入属性信息列表
        List<Long> attrValueIds = roomSubmitVo.getAttrValueIds();
        if (!CollectionUtils.isEmpty(attrValueIds)) {
            List<RoomAttrValue> attrValueList = new ArrayList<>();
            for (Long attrValueId : attrValueIds) {
                RoomAttrValue roomAttrValue = RoomAttrValue.builder()
                        .roomId(roomSubmitVo.getId())
                        .attrValueId(attrValueId)
                        .build();
                attrValueList.add(roomAttrValue);
            }
            roomAttrValueService.saveBatch(attrValueList);
        }

        // 3. 插入配套信息列表
        List<Long> facilityInfoIds = roomSubmitVo.getFacilityInfoIds();
        if (!CollectionUtils.isEmpty(facilityInfoIds)) {
            List<RoomFacility> facilityList = new ArrayList<>();
            for (Long facilityInfoId : facilityInfoIds) {
                RoomFacility roomFacility = RoomFacility.builder()
                        .roomId(roomSubmitVo.getId())
                        .facilityId(facilityInfoId)
                        .build();
                facilityList.add(roomFacility);
            }
            roomFacilityService.saveBatch(facilityList);
        }

        // 4. 插入标签信息列表
        List<Long> labelInfoIds = roomSubmitVo.getLabelInfoIds();
        if (!CollectionUtils.isEmpty(labelInfoIds)) {
            List<RoomLabel> labelList = new ArrayList<>();
            for (Long labelInfoId : labelInfoIds) {
                RoomLabel roomLabel = RoomLabel.builder()
                        .roomId(roomSubmitVo.getId())
                        .labelId(labelInfoId)
                        .build();
                labelList.add(roomLabel);
            }
            roomLabelService.saveBatch(labelList);
        }

        // 5. 插入支付方式列表
        List<Long> paymentTypeIds = roomSubmitVo.getPaymentTypeIds();
        if (!CollectionUtils.isEmpty(paymentTypeIds)) {
            List<RoomPaymentType> paymentTypeList = new ArrayList<>();
            for (Long paymentTypeId : paymentTypeIds) {
                RoomPaymentType paymentType = RoomPaymentType.builder()
                        .roomId(roomSubmitVo.getId())
                        .paymentTypeId(paymentTypeId)
                        .build();
                paymentTypeList.add(paymentType);
            }
            roomPaymentTypeService.saveBatch(paymentTypeList);
        }

        // 6. 插入可选租期列表
        List<Long> leaseTermIds = roomSubmitVo.getLeaseTermIds();
        if (!CollectionUtils.isEmpty(leaseTermIds)) {
            List<RoomLeaseTerm> leaseTermList = new ArrayList<>();
            for (Long leaseTermId : leaseTermIds) {
                RoomLeaseTerm leaseTerm = RoomLeaseTerm.builder()
                        .roomId(roomSubmitVo.getId())
                        .leaseTermId(leaseTermId)
                        .build();
                leaseTermList.add(leaseTerm);
            }
            roomLeaseTermService.saveBatch(leaseTermList);
        }
    }
}




