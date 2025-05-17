package com.ryan.lease.web.admin.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryan.lease.model.entity.SystemUser;
import com.ryan.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.ryan.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
* @author liubo
* @description 针对表【system_user(员工信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.ryan.lease.model.SystemUser
*/
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    /**
     * 根据条件分页查询后台用户列表
     * @param page 分页条件
     * @param queryVo 查询条件
     * @return IPage<SystemUserItemVo>
     */
    IPage<SystemUserItemVo> pageSystemUser(Page<SystemUser> page, SystemUserQueryVo queryVo);

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return SystemUser
     */
    SystemUser selectOneByUsername(String username);
}




