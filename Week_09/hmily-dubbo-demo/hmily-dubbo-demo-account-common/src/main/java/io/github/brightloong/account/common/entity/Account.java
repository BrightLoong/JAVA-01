package io.github.brightloong.account.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author BrightLoong
 * @date 2021/3/15 21:08
 * @description
 */

@Data
public class Account implements Serializable {

    private static final long serialVersionUID = 6957734749389133832L;

    private Long id;

    private Long userId;

    private Integer accountType;

    private BigDecimal accountBalance;

    private BigDecimal accountBalanceLocked;

}
