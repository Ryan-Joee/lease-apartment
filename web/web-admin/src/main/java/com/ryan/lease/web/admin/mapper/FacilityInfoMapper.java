package com.ryan.lease.web.admin.mapper;

import com.ryan.lease.model.entity.FacilityInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【facility_info(配套信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.ryan.lease.model.FacilityInfo
*/
public interface FacilityInfoMapper extends BaseMapper<FacilityInfo> {

    /**
     * 查询配套列表
     * @param id 公寓id
     * @return List<FacilityInfo>
     */
    List<FacilityInfo> selectListByApartmentId(Long id);
}




