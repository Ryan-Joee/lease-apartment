package com.ryan.lease.web.admin.mapper;

import com.ryan.lease.model.entity.AttrValue;
import com.ryan.lease.web.admin.vo.attr.AttrValueVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【attr_value(房间基本属性值表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.ryan.lease.model.AttrValue
*/
public interface AttrValueMapper extends BaseMapper<AttrValue> {

    /**
     * 根据房间id查询AttrValueVo
     * @param id 房间id
     * @return List<AttrValueVo>
     */
    List<AttrValueVo> selectListByRoomId(Long id);
}




