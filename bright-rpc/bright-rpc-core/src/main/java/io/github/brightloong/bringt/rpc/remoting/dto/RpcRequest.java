package io.github.brightloong.bringt.rpc.remoting.dto;

import lombok.Data;

/**
 * @author BrightLoong
 * @date 2021/3/18 21:15
 * @description
 */
@Data
public class RpcRequest {

    private String serviceClass;

    private String methodName;

    private Object[] parameters;

    private Class<?>[] paramTypes;

}
