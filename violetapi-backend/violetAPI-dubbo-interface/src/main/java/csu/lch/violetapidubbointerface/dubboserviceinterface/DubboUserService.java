package csu.lch.violetapidubbointerface.dubboserviceinterface;


import csu.lch.violetapidubbointerface.entity.User;

/**
 * 用户服务
 *
 * @author yupi
 */
public interface DubboUserService {

    /**
     * 用户鉴权
     *
     * @param accessKey   ak标识
     * @return  调用者
     */
    User getInvokeUser(String accessKey);
}
