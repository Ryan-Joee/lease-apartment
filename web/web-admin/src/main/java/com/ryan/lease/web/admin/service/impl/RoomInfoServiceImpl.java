package com.ryan.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ryan.lease.model.entity.*;
import com.ryan.lease.model.enums.ItemType;
import com.ryan.lease.web.admin.mapper.*;
import com.ryan.lease.web.admin.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryan.lease.web.admin.vo.attr.AttrValueVo;
import com.ryan.lease.web.admin.vo.graph.GraphVo;
import com.ryan.lease.web.admin.vo.room.RoomDetailVo;
import com.ryan.lease.web.admin.vo.room.RoomItemVo;
import com.ryan.lease.web.admin.vo.room.RoomQueryVo;
import com.ryan.lease.web.admin.vo.room.RoomSubmitVo;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;

    @Autowired
    private GraphInfoMapper graphInfoMapper;

    @Autowired
    private AttrValueMapper attrValueMapper;

    @Autowired
    private FacilityInfoMapper facilityInfoMapper;

    @Autowired
    private LabelInfoMapper labelInfoMapper;

    @Autowired
    private PaymentTypeMapper paymentTypeMapper;

    @Autowired
    private LeaseTermMapper leaseTermMapper;

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
                graphInfo.setItemId(roomSubmitVo.getId());
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

    /**
     * 根据条件分页查询房间列表
     * @param page 分页条件
     * @param queryVo 分页条件
     * @return  IPage<RoomItemVo>
     */
    @Override
    public IPage<RoomItemVo> pageRoomItemByQuery(IPage<RoomItemVo> page, RoomQueryVo queryVo) {
        return roomInfoMapper.pageRoomItemByQuery(page, queryVo);
    }

    /**
     * 根据id获取房间详细信息
     * @param id 房间id
     * @return RoomDetailVo
     */
    @Override
    public RoomDetailVo getRoomDetailById(Long id) {

        //1.查询RoomInfo
        RoomInfo roomInfo = roomInfoMapper.selectById(id);

        //2.查询所属公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(roomInfo.getApartmentId());

        //3.查询graphInfoList
        List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM, id);

        //4.查询attrValueList
        List<AttrValueVo> attrvalueVoList = attrValueMapper.selectListByRoomId(id);

        //5.查询facilityInfoList
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectListByRoomId(id);

        //6.查询labelInfoList
        List<LabelInfo> labelInfoList = labelInfoMapper.selectListByRoomId(id);

        //7.查询paymentTypeList
        List<PaymentType> paymentTypeList = paymentTypeMapper.selectListByRoomId(id);

        //8.查询leaseTermList
        List<LeaseTerm> leaseTermList = leaseTermMapper.selectListByRoomId(id);


        RoomDetailVo adminRoomDetailVo = new RoomDetailVo();
        BeanUtils.copyProperties(roomInfo, adminRoomDetailVo);

        adminRoomDetailVo.setApartmentInfo(apartmentInfo);
        adminRoomDetailVo.setGraphVoList(graphVoList);
        adminRoomDetailVo.setAttrValueVoList(attrvalueVoList);
        adminRoomDetailVo.setFacilityInfoList(facilityInfoList);
        adminRoomDetailVo.setLabelInfoList(labelInfoList);
        adminRoomDetailVo.setPaymentTypeList(paymentTypeList);
        adminRoomDetailVo.setLeaseTermList(leaseTermList);

        return adminRoomDetailVo;
    }

    /**
     * 根据id删除房间信息
     * @param id 房间id
     */
    @Override
    public void removeRoomById(Long id) {
        //1.删除RoomInfo
        super.removeById(id);

        //2.删除graphInfoList
        LambdaQueryWrapper<GraphInfo> graphQueryWrapper = new LambdaQueryWrapper<>();
        graphQueryWrapper.eq(GraphInfo::getItemType, ItemType.ROOM);
        graphQueryWrapper.eq(GraphInfo::getItemId, id);
        graphInfoService.remove(graphQueryWrapper);

        //3.删除attrValueList
        LambdaQueryWrapper<RoomAttrValue> attrQueryWrapper = new LambdaQueryWrapper<>();
        attrQueryWrapper.eq(RoomAttrValue::getRoomId, id);
        roomAttrValueService.remove(attrQueryWrapper);

        //4.删除facilityInfoList
        LambdaQueryWrapper<RoomFacility> facilityQueryWrapper = new LambdaQueryWrapper<>();
        facilityQueryWrapper.eq(RoomFacility::getRoomId, id);
        roomFacilityService.remove(facilityQueryWrapper);

        //5.删除labelInfoList
        LambdaQueryWrapper<RoomLabel> labelQueryWrapper = new LambdaQueryWrapper<>();
        labelQueryWrapper.eq(RoomLabel::getRoomId, id);
        roomLabelService.remove(labelQueryWrapper);

        //6.删除paymentTypeList
        LambdaQueryWrapper<RoomPaymentType> paymentQueryWrapper = new LambdaQueryWrapper<>();
        paymentQueryWrapper.eq(RoomPaymentType::getRoomId, id);
        roomPaymentTypeService.remove(paymentQueryWrapper);

        //7.删除leaseTermList
        LambdaQueryWrapper<RoomLeaseTerm> termQueryWrapper = new LambdaQueryWrapper<>();
        termQueryWrapper.eq(RoomLeaseTerm::getRoomId, id);
        roomLeaseTermService.remove(termQueryWrapper);
    }
}




