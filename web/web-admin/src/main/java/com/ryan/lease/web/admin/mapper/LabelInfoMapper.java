package com.ryan.lease.web.admin.mapper;

import com.ryan.lease.model.entity.LabelInfo;
import com.ryan.lease.model.enums.ItemType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【label_info(标签信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.ryan.lease.model.LabelInfo
*/
public interface LabelInfoMapper extends BaseMapper<LabelInfo> {

    /**
     * 查询标签列表
     * @param id 公寓id
     * @return List<LabelInfo>
     */
    List<LabelInfo> selectListByApartmentId(Long id);
}




