package csu.lch.violetapi.model.vo;

import csu.lch.violetapidubbointerface.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口调用分析（top3）视图
 *
 * @TableName InterfaceInfo
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InvokeInterfaceInfoVO extends InterfaceInfo {

    /**
     * 接口调用次数
     */
    private Integer invokeNum;
}