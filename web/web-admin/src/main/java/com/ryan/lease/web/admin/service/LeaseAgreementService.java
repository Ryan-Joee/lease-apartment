package com.ryan.lease.web.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryan.lease.model.entity.LeaseAgreement;
import com.ryan.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.ryan.lease.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface LeaseAgreementService extends IService<LeaseAgreement> {

    /**
     * 根据条件分页查询租约列表
     * @param page 分页条件
     * @param queryVo 查询条件
     * @return IPage<AgreementVo>
     */
    IPage<AgreementVo> pageAgreement(Page<AgreementVo> page, AgreementQueryVo queryVo);

    /**
     * 根据id查询租约信息
     * @param id 租约id
     * @return AgreementVo
     */
    AgreementVo getAgreementById(Long id);
}
