package io.github.brightloong.bringt.rpc.common.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author BrightLoong
 * @date 2021/3/19 22:10
 * @description
 */
@Builder
@Data
public class ServiceProviderDesc {

    private String host;

    private Integer port;

    private String serviceClass;
}
