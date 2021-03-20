package io.github.brightloong.bringt.rpc.remoting.dto;

import lombok.Data;

/**
 * @author BrightLoong
 * @date 2021/3/18 21:12
 * @description
 */

@Data
public class RpcResponse {

    private Object result;

    private Boolean status;

    private Exception exception;
}
