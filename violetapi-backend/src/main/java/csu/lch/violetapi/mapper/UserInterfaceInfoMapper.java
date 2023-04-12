package csu.lch.violetapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import csu.lch.violetapidubbointerface.entity.UserInterfaceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Administrator
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2023-04-05 09:21:00
* @Entity csu.lch.violetAPI.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




