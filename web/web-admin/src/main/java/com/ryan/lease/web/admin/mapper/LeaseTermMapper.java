package com.ryan.lease.web.admin.mapper;

import com.ryan.lease.model.entity.LeaseTerm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【lease_term(租期)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.ryan.lease.model.LeaseTerm
*/
public interface LeaseTermMapper extends BaseMapper<LeaseTerm> {

    /**
     * 根据房间id查询LeaseTerm
     * @param id 房间id
     * @return List<LeaseTerm>>
     */
    List<LeaseTerm> selectListByRoomId(Long id);
}




