package csu.lch.violetapi.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口调用请求
 *
 * @TableName product
 */

@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 请求参数
     * [
     *     {"name":"username","type":"String"}
     * ]
     */
    private String requestParams;


    private static final long serialVersionUID = 1L;
}
