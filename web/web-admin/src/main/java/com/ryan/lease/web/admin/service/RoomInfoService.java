package com.ryan.lease.web.admin.service;

import com.ryan.lease.model.entity.RoomInfo;
import com.ryan.lease.web.admin.vo.room.RoomDetailVo;
import com.ryan.lease.web.admin.vo.room.RoomItemVo;
import com.ryan.lease.web.admin.vo.room.RoomQueryVo;
import com.ryan.lease.web.admin.vo.room.RoomSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface RoomInfoService extends IService<RoomInfo> {

    /**
     * 保存或更新房间信息
     * @param roomSubmitVo 房间详细信息
     */
    void saveOrUpdateRoom(RoomSubmitVo roomSubmitVo);

    /**
     * 根据条件分页查询房间列表
     * @param page 分页条件
     * @param queryVo 分页条件
     * @return  IPage<RoomItemVo>
     */
    IPage<RoomItemVo> pageRoomItemByQuery(IPage<RoomItemVo> page, RoomQueryVo queryVo);

    /**
     * 根据id获取房间详细信息
     * @param id 房间id
     * @return RoomDetailVo
     */
    RoomDetailVo getRoomDetailById(Long id);

    /**
     * 根据id删除房间信息
     * @param id 房间id
     */
    void removeRoomById(Long id);
}
