package csu.lch.violetapidubbointerface.dubboserviceinterface;

import csu.lch.violetapidubbointerface.entity.InterfaceInfo;

/**
 *
 */
public interface DubboInterfaceInfoService {

    /**
     * 从数据库中查询模拟接口是否存在，以及请求方法是否匹配
     *
     * @param url   请求接口路径
     * @param method    请求方法
     * @return  调用的接口
     */
    InterfaceInfo getInterfaceInfo(String url, String method);
}
