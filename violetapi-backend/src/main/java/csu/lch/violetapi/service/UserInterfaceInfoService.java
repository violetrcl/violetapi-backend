package csu.lch.violetapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import csu.lch.violetapidubbointerface.entity.UserInterfaceInfo;

/**
* @author Administrator
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2023-04-05 09:21:00
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    /**
     * 校验
     *
     * @param userinterfaceInfo     用户调用接口关系
     * @param add
     */
    void validUserInterfaceInfo(UserInterfaceInfo userinterfaceInfo, boolean add);

    boolean invokeCount(long interfaceInfoId, long userId);
}
