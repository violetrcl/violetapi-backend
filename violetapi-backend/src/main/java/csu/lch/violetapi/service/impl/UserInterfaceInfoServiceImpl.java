package csu.lch.violetapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import csu.lch.violetapi.common.ErrorCode;
import csu.lch.violetapi.exception.BusinessException;

import csu.lch.violetapi.service.UserInterfaceInfoService;
import csu.lch.violetapi.mapper.UserInterfaceInfoMapper;
import csu.lch.violetapidubbointerface.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2023-04-05 09:21:00
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建时，所有参数必须非空
        if (add) {// TODO: 2023/4/5 这里只是简单校验，后续补充
            if (userInterfaceInfo.getInterfaceInfoId() <= 0 || userInterfaceInfo.getUserId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
            }
        }
        if (userInterfaceInfo.getLeftNum() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于 0");
        }
    }

    /**
     * 用户调用接口次数统计
     *
     * @param interfaceInfoId   用户调用的接口id
     * @param userId    用户id
     * @return      用户调用接口次数更新是否成功
     */
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        UpdateWrapper<UserInterfaceInfo> userInterfaceInfoUpdateWrapper = new UpdateWrapper<>();
        userInterfaceInfoUpdateWrapper.eq("interfaceInfoId", interfaceInfoId);
        userInterfaceInfoUpdateWrapper.eq("userId", userId);
        userInterfaceInfoUpdateWrapper.gt("leftNum", 0);
// TODO: 2023/4/5 这里的逻辑其实没那么简单，还要考虑并发，要加锁之类的，可以参考伙伴匹配系统
        userInterfaceInfoUpdateWrapper.setSql("leftNum = leftNum - 1, invokeNum = invokeNum + 1");
        return this.update(userInterfaceInfoUpdateWrapper);// TODO: 2023/4/5 这个this可以不加
    }
}





