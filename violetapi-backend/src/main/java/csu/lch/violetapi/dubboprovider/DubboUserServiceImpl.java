package csu.lch.violetapi.dubboprovider;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import csu.lch.violetapi.common.ErrorCode;
import csu.lch.violetapi.exception.BusinessException;
import csu.lch.violetapi.mapper.UserMapper;
import csu.lch.violetapidubbointerface.dubboserviceinterface.DubboUserService;
import csu.lch.violetapidubbointerface.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class DubboUserServiceImpl implements DubboUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isAnyBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey", accessKey);
        return userMapper.selectOne(queryWrapper);
    }
}
