package com.ryan.lease.web.admin.mapper;

import com.ryan.lease.model.entity.ApartmentInfo;
import com.ryan.lease.model.enums.LeaseStatus;
import com.ryan.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.ryan.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.ryan.lease.model.ApartmentInfo
*/
public interface ApartmentInfoMapper extends BaseMapper<ApartmentInfo> {

    IPage<ApartmentItemVo> pageItem(Page page, ApartmentQueryVo queryVo);
}




