package csu.lch.violetapi.dubboprovider;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import csu.lch.violetapi.common.ErrorCode;
import csu.lch.violetapi.exception.BusinessException;

import csu.lch.violetapi.mapper.InterfaceInfoMapper;
import csu.lch.violetapidubbointerface.dubboserviceinterface.DubboInterfaceInfoService;
import csu.lch.violetapidubbointerface.entity.InterfaceInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class DubboInterfaceInfoServiceImpl implements DubboInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if (StringUtils.isAnyBlank(url, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        queryWrapper.eq("method", method);
        return interfaceInfoMapper.selectOne(queryWrapper);
    }
}
