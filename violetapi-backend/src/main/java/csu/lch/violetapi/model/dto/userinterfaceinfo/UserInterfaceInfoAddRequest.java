package csu.lch.violetapi.model.dto.userinterfaceinfo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户接口关系新增请求体
 *
 * @TableName product
 */

@Data
public class UserInterfaceInfoAddRequest implements Serializable {

    /**
     * 调用用户 id
     */
    private Long userId;

    /**
     * 接口 id
     */
    private Long interfaceInfoId;

    /**
     * 总调用次数
     */
    private Integer invokeNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}