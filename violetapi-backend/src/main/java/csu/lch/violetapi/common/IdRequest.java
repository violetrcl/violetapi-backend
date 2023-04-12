package csu.lch.violetapi.common;

import lombok.Data;

import java.io.Serializable;

/**
 * id通用请求体
 *
 * @author violetRcl
 */
@Data
public class IdRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}