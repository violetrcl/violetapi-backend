package csu.lch.violetapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import csu.lch.violetapi.annotation.AuthCheck;
import csu.lch.violetapi.common.BaseResponse;
import csu.lch.violetapi.common.ErrorCode;
import csu.lch.violetapi.common.ResultUtils;
import csu.lch.violetapi.exception.BusinessException;
import csu.lch.violetapi.mapper.UserInterfaceInfoMapper;
import csu.lch.violetapi.model.vo.InvokeInterfaceInfoVO;
import csu.lch.violetapi.service.InterfaceInfoService;
import csu.lch.violetapidubbointerface.entity.InterfaceInfo;
import csu.lch.violetapidubbointerface.entity.UserInterfaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 接口调用分析控制器
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InvokeInterfaceInfoVO>> listTopInvokeInterfaceInfo() {
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", interfaceInfoIdObjMap.keySet());
        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<InvokeInterfaceInfoVO> invokeInterfaceInfoVOList = list.stream().map(interfaceInfo -> {
            InvokeInterfaceInfoVO invokeInterfaceInfoVO = new InvokeInterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, invokeInterfaceInfoVO);
            int invokeNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getInvokeNum();
            invokeInterfaceInfoVO.setInvokeNum(invokeNum);
            return invokeInterfaceInfoVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(invokeInterfaceInfoVOList);
    }
}
