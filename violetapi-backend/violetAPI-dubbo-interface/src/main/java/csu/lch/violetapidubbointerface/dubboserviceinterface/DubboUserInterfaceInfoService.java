package csu.lch.violetapidubbointerface.dubboserviceinterface;

/**
 *
 */
public interface DubboUserInterfaceInfoService {

    /**
     * 接口调用次数统计
     *
     * @param interfaceInfoId   调用接口id
     * @param userId    调用者id
     * @return  接口调用次数统计是否成功
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
