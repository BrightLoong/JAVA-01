package io.github.brightloong.account.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author BrightLoong
 * @date 2021/3/15 21:17
 * @description
 */

@Data
public class AccountDTO implements Serializable {

    /**
     * 操作账户uid
     */
    private Long userId;

    private Long targeUid;


    /**
     * 要换的钱的类型
     */
    private Integer sourceAccountType;

    /**
     * 准备换的钱的类型
     */
    private Integer targetAccountType;

    /**
     * 要换多少钱比如：1美元换7人命币，1
     */
    private BigDecimal sourceMoney;

    /**
     * 要换多少钱比如：1美元换7人命币，7
     */
    private BigDecimal targetMoney;
}
