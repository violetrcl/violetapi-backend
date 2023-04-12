package csu.lch.violetapi.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除通用请求体
 *
 * @author violetRcl
 */
@Data
public class DeleteRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}