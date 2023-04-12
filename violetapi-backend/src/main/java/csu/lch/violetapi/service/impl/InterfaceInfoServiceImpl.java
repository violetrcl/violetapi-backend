package csu.lch.violetapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import csu.lch.violetapi.common.ErrorCode;
import csu.lch.violetapi.exception.BusinessException;
import csu.lch.violetapi.mapper.InterfaceInfoMapper;
import csu.lch.violetapi.service.InterfaceInfoService;
import csu.lch.violetapidubbointerface.entity.InterfaceInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author violetRcl
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2023-03-27 18:39:33
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

//    @Override
//    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
//
//        if (interfaceInfo == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//
//        Long id = interfaceInfo.getId();
//        String name = interfaceInfo.getName();
//        String description = interfaceInfo.getDescription();
//        String url = interfaceInfo.getUrl();
//        String requestHeader = interfaceInfo.getRequestHeader();
//        String responseHeader = interfaceInfo.getResponseHeader();
//        Integer status = interfaceInfo.getStatus();
//        String method = interfaceInfo.getMethod();
//        Long userId = interfaceInfo.getUserId();
//        Date createTime = interfaceInfo.getCreateTime();
//        Date updateTime = interfaceInfo.getUpdateTime();
//        Integer isDelete = interfaceInfo.getIsDelete();
//
//        // 创建时，参数不能为空
//        if (add) {
//
//        }
//        // 有参数则校验
//        if (StringUtils.isNotBlank(name) && name.length() > 80) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口名称过长");
//        }
//        if (StringUtils.isNotBlank(name) && name.length() > 8192) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口名称过长");
//        }
//    }
    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        // 创建时，所有参数必须非空
        if (add) {// TODO: 2023/4/5 这里只是简单校验，后续补充
            if (StringUtils.isAnyBlank(name)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口名称过长");
        }
    }
}




