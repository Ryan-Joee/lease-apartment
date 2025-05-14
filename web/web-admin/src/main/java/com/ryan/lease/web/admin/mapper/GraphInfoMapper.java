package com.ryan.lease.web.admin.mapper;

import com.ryan.lease.model.entity.GraphInfo;
import com.ryan.lease.model.enums.ItemType;
import com.ryan.lease.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【graph_info(图片信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.ryan.lease.model.GraphInfo
*/
public interface GraphInfoMapper extends BaseMapper<GraphInfo> {

    /**
     * 根据图片类型和id查询图片列表
     * @param itemType 图片类型
     * @param id 图片id
     * @return List<GraphVo>
     */
    List<GraphVo> selectListByItemTypeAndId(ItemType itemType, Long id);
}




