package com.ryan.lease.web.admin.service;

import com.ryan.lease.model.entity.AttrKey;
import com.ryan.lease.web.admin.vo.attr.AttrKeyVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liubo
* @description 针对表【attr_key(房间基本属性表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface AttrKeyService extends IService<AttrKey> {

    /**
     * 查询全部属性名称和属性值列表
     * @return AttrKeyVo
     */
    List<AttrKeyVo> listAttrInfo();
}
