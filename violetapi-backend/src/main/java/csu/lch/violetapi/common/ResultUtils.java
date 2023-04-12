package csu.lch.violetapi.common;

/**
 * 返回工具类
 *
 * @author violetRcl
 */
@SuppressWarnings("unchecked")
public class ResultUtils {

    /**
     * 成功
     *
     * @param data  返回数据
     * @param <T>   返回数据类型（泛型）
     * @return  成功通用返回
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return  失败通用返回
     */
    public BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param code  错误码
     * @param message   错误码描述
     * @return  失败通用返回
     */
    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, null, message);
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return  失败通用返回
     */
    public static BaseResponse error(ErrorCode errorCode, String message) {
        return new BaseResponse(errorCode.getCode(), null, message);
    }
}
