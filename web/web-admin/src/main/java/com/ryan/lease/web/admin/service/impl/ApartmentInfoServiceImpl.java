package com.ryan.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryan.lease.common.exception.LeaseException;
import com.ryan.lease.common.result.ResultCodeEnum;
import com.ryan.lease.model.entity.*;
import com.ryan.lease.model.enums.ItemType;
import com.ryan.lease.web.admin.mapper.*;
import com.ryan.lease.web.admin.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryan.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.ryan.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.ryan.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.ryan.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.ryan.lease.web.admin.vo.fee.FeeValueVo;
import com.ryan.lease.web.admin.vo.graph.GraphVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {


    @Autowired
    private GraphInfoService graphInfoService;

    @Autowired
    private ApartmentFacilityService apartmentFacilityService;

    @Autowired
    private ApartmentLabelService apartmentLabelService;

    @Autowired
    private ApartmentFeeValueService apartmentFeeValueService;

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;

    @Autowired
    private GraphInfoMapper graphInfoMapper;

    @Autowired
    private LabelInfoMapper labelInfoMapper;

    @Autowired
    private FacilityInfoMapper facilityInfoMapper;

    @Autowired
    private FeeValueMapper feeValueMapper;

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    /**
     * "保存或更新公寓信息"
     * @param apartmentSubmitVo 公寓信息
     */
    @Override
    public void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo) {
        boolean isUpdate = apartmentSubmitVo.getId() != null;
        // super ==> ServiceImpl
        // 传入的参数：apartmentSubmitVo继承了ApartmentInfo，
        // 所以可以用ServiceImpl提供的saveOrUpdate()方法操作apartmentSubmitVo中有关ApartmentInfo的数据
        super.saveOrUpdate(apartmentSubmitVo);

        // 更新公寓信息的逻辑：先删除原有的信息，再插入新信息
        if (isUpdate) {
            // 1.删除图片列表
            LambdaQueryWrapper<GraphInfo> graphQueryWrapper = Wrappers.lambdaQuery(GraphInfo.class)
                    .eq(GraphInfo::getItemType, ItemType.APARTMENT)
                    // 表示这个图片属于哪个公寓
                    .eq(GraphInfo::getItemId, apartmentSubmitVo.getId());
            graphInfoService.remove(graphQueryWrapper);

            // 2.删除配套列表
            LambdaQueryWrapper<ApartmentFacility> facilityQueryWrapper = Wrappers.lambdaQuery(ApartmentFacility.class)
                    .eq(ApartmentFacility::getApartmentId, apartmentSubmitVo.getId());
            apartmentFacilityService.remove(facilityQueryWrapper);

            // 3.删除标签列表
            LambdaQueryWrapper<ApartmentLabel> labelQueryWrapper = Wrappers.lambdaQuery(ApartmentLabel.class)
                    .eq(ApartmentLabel::getApartmentId, apartmentSubmitVo.getId());
            apartmentLabelService.remove(labelQueryWrapper);

            // 4.删除杂费列表
            LambdaQueryWrapper<ApartmentFeeValue> feeQueryWrapper = Wrappers.lambdaQuery(ApartmentFeeValue.class)
                    .eq(ApartmentFeeValue::getApartmentId, apartmentSubmitVo.getId());
            apartmentFeeValueService.remove(feeQueryWrapper);
        }

        // 1.插入图片列表
        List<GraphVo> graphVoList = apartmentSubmitVo.getGraphVoList();
        if (!CollectionUtils.isEmpty(graphVoList)) {
            List<GraphInfo>  graphInfoList = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setId(apartmentSubmitVo.getId());
                graphInfo.setItemType(ItemType.APARTMENT);
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfoList.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfoList);
        }

        // 2.插入配套列表
        List<Long> facilityInfoIdList = apartmentSubmitVo.getFacilityInfoIds();
        if (!CollectionUtils.isEmpty(facilityInfoIdList)) {
            List<ApartmentFacility> facilityList = new ArrayList<>();
            for (Long facilityId : facilityInfoIdList) {
                // ApartmentFacility类上加了@Builder注解
                // 用于实现 “建造者模式”（Builder Pattern），能用链式语法优雅地构造对象，特别适合属性较多的类。
                ApartmentFacility apartmentFacility =  ApartmentFacility.builder()
                        .apartmentId(apartmentSubmitVo.getId())
                        .facilityId(facilityId)
                        .build();
                facilityList.add(apartmentFacility);
            }
            apartmentFacilityService.saveBatch(facilityList);
        }

        // 3.插入标签列表
        List<Long> labelIdList = apartmentSubmitVo.getLabelIds();
        if (!CollectionUtils.isEmpty(labelIdList)) {
            List<ApartmentLabel> apartmentLabelList = new ArrayList<>();
            for (Long labelId : labelIdList) {
                ApartmentLabel apartmentLabel = ApartmentLabel.builder()
                        .apartmentId(apartmentSubmitVo.getId())
                        .labelId(labelId)
                        .build();
                apartmentLabelList.add(apartmentLabel);
            }
            apartmentLabelService.saveBatch(apartmentLabelList);
        }

        // 4.插入杂费列表
        List<Long> feeValueIdList = apartmentSubmitVo.getFeeValueIds();
        if (!CollectionUtils.isEmpty(feeValueIdList)) {
            List<ApartmentFeeValue> apartmentFeeValueList = new ArrayList<>();
            for (Long feeValueId : feeValueIdList) {
                ApartmentFeeValue apartmentFeeValue = ApartmentFeeValue.builder()
                        .apartmentId(apartmentSubmitVo.getId())
                        .feeValueId(feeValueId)
                        .build();
                apartmentFeeValueList.add(apartmentFeeValue);
            }
            apartmentFeeValueService.saveBatch(apartmentFeeValueList);
        }
    }

    /**
     * 根据条件分页查询公寓列表
     * @param page 分页条件
     * @param queryVo 分页条件
     * @return 分页查询的结果
     */
    @Override
    public IPage<ApartmentItemVo> pageItem(Page page, ApartmentQueryVo queryVo) {
        return apartmentInfoMapper.pageItem(page, queryVo);
    }

    /**
     * 根据ID获取公寓详细信息
     * @param id 前端传来的公寓id
     * @return ApartmentDetailVo
     */
    @Override
    public ApartmentDetailVo getDetailById(Long id) {
        // 1. 查询公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(id);
        // 2. 查询图片列表
        List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT, id);

        // 3. 查询标签列表
        List<LabelInfo> labelInfoList = labelInfoMapper.selectListByApartmentId(id);

        // 4. 查询配套列表
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectListByApartmentId(id);

        // 5. 查询杂费列表
        List<FeeValueVo> feeValueList = feeValueMapper.selectListByApartmentId(id);

        // 6. 组装结果
        ApartmentDetailVo apartmentDetailVo = new ApartmentDetailVo();
        BeanUtils.copyProperties(apartmentInfo, apartmentDetailVo);
        apartmentDetailVo.setGraphVoList(graphVoList);
        apartmentDetailVo.setLabelInfoList(labelInfoList);
        apartmentDetailVo.setFacilityInfoList(facilityInfoList);
        apartmentDetailVo.setFeeValueVoList(feeValueList);

        return apartmentDetailVo;
    }

    /**
     * 根据id删除公寓信息
     * @param id 公寓id
     */
    @Override
    public void removeAartmentById(Long id) {

        // 统计该公寓下的房间个数
        LambdaQueryWrapper<RoomInfo> roomQueryWrapper = Wrappers.lambdaQuery(RoomInfo.class).eq(RoomInfo::getApartmentId, id);
        Long roomCount = roomInfoMapper.selectCount(roomQueryWrapper);
        if (roomCount > 0) {
            // 终止删除，并响应提示信息
            throw new LeaseException(ResultCodeEnum.ADMIN_APARTMENT_DELETE_ROOM);
        }

        super.removeById(id);
        // 1.删除图片列表
        LambdaQueryWrapper<GraphInfo> graphQueryWrapper = Wrappers.lambdaQuery(GraphInfo.class)
                .eq(GraphInfo::getItemType, ItemType.APARTMENT)
                // 表示这个图片属于哪个公寓
                .eq(GraphInfo::getItemId, id);
        graphInfoService.remove(graphQueryWrapper);

        // 2.删除配套列表
        LambdaQueryWrapper<ApartmentFacility> facilityQueryWrapper = Wrappers.lambdaQuery(ApartmentFacility.class)
                .eq(ApartmentFacility::getApartmentId, id);
        apartmentFacilityService.remove(facilityQueryWrapper);

        // 3.删除标签列表
        LambdaQueryWrapper<ApartmentLabel> labelQueryWrapper = Wrappers.lambdaQuery(ApartmentLabel.class)
                .eq(ApartmentLabel::getApartmentId, id);
        apartmentLabelService.remove(labelQueryWrapper);

        // 4.删除杂费列表
        LambdaQueryWrapper<ApartmentFeeValue> feeQueryWrapper = Wrappers.lambdaQuery(ApartmentFeeValue.class)
                .eq(ApartmentFeeValue::getApartmentId, id);
        apartmentFeeValueService.remove(feeQueryWrapper);
    }
}




