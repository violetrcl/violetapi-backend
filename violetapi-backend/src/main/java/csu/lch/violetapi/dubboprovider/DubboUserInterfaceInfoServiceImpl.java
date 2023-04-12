package csu.lch.violetapi.dubboprovider;

import csu.lch.violetapi.service.UserInterfaceInfoService;
import csu.lch.violetapidubbointerface.dubboserviceinterface.DubboUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class DubboUserInterfaceInfoServiceImpl implements DubboUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }
}
