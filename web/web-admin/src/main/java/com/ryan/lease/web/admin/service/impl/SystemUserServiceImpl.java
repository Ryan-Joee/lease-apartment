package com.ryan.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryan.lease.common.exception.LeaseException;
import com.ryan.lease.common.result.ResultCodeEnum;
import com.ryan.lease.model.entity.SystemPost;
import com.ryan.lease.model.entity.SystemUser;
import com.ryan.lease.web.admin.mapper.SystemPostMapper;
import com.ryan.lease.web.admin.mapper.SystemUserMapper;
import com.ryan.lease.web.admin.service.SystemUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryan.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.ryan.lease.web.admin.vo.system.user.SystemUserQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【system_user(员工信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
        implements SystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private SystemPostMapper systemPostMapper;

    /**
     * 根据条件分页查询后台用户列表
     * @param page 分页条件
     * @param queryVo 查询条件
     * @return IPage<SystemUserItemVo>
     */
    @Override
    public IPage<SystemUserItemVo> pageSystemUser(Page<SystemUser> page, SystemUserQueryVo queryVo) {
        return systemUserMapper.pageSystemUser(page, queryVo);
    }

    /**
     * 根据ID查询后台用户信息
     * @param id 用户ID
     * @return SystemUserItemVo
     */
    @Override
    public SystemUserItemVo getSystemUserById(Long id) {
        if (id == null) {
            throw new LeaseException(ResultCodeEnum.PARAM_ERROR);
        }
        SystemUser systemUser = systemUserMapper.selectById(id);
        if (systemUser == null) {
            throw new LeaseException(ResultCodeEnum.DATA_ERROR);
        }
        SystemPost systemPost = systemPostMapper.selectById(systemUser.getPostId());
        if (systemPost == null) {
            throw new LeaseException(ResultCodeEnum.DATA_ERROR);
        }
        SystemUserItemVo systemUserItemVo = new SystemUserItemVo();
        BeanUtils.copyProperties(systemUser, systemUserItemVo);
        systemUserItemVo.setPostName(systemPost.getName());
        return systemUserItemVo;
    }
}




