package com.ryan.lease.web.admin.service;

import com.ryan.lease.model.entity.ApartmentInfo;
import com.ryan.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.ryan.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.ryan.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.ryan.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface ApartmentInfoService extends IService<ApartmentInfo> {

    /**
     * "保存或更新公寓信息"
     * @param apartmentSubmitVo 公寓信息
     */
    void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo);

    /**
     * 根据条件分页查询公寓列表
     * @param page 分页条件
     * @param queryVo 分页条件
     * @return 分页查询的结果
     */
    IPage<ApartmentItemVo> pageItem(Page page, ApartmentQueryVo queryVo);
}
