package com.ryan.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryan.lease.model.entity.DistrictInfo;
import com.ryan.lease.web.app.service.DistrictInfoService;
import com.ryan.lease.web.app.mapper.DistrictInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author liubo
* @description 针对表【district_info】的数据库操作Service实现
* @createDate 2023-07-26 11:12:39
*/
@Service
public class DistrictInfoServiceImpl extends ServiceImpl<DistrictInfoMapper, DistrictInfo>
    implements DistrictInfoService{

}




