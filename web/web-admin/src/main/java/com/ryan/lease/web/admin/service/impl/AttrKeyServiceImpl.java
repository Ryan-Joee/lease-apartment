package com.ryan.lease.web.admin.service.impl;

import com.ryan.lease.model.entity.AttrKey;
import com.ryan.lease.web.admin.mapper.AttrKeyMapper;
import com.ryan.lease.web.admin.service.AttrKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryan.lease.web.admin.vo.attr.AttrKeyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【attr_key(房间基本属性表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class AttrKeyServiceImpl extends ServiceImpl<AttrKeyMapper, AttrKey>
    implements AttrKeyService{

    /**
     * 这里会报错，是因为idea无法识别@MapperScan注解，但是不影响使用
     */
    @Autowired
    private AttrKeyMapper attrKeyMapper;

    /**
     * 查询全部属性名称和属性值列表
     * @return AttrKeyVo
     */
    @Override
    public List<AttrKeyVo> listAttrInfo() {
        return attrKeyMapper.listAttrInfo();
    }
}




