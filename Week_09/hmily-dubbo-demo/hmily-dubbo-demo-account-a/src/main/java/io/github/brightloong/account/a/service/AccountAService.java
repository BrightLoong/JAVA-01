package io.github.brightloong.account.a.service;

import java.math.BigDecimal;

/**
 * @author BrightLoong
 * @date 2021/3/16 21:34
 * @description
 */
public interface AccountAService {
    boolean changeMoney(BigDecimal money, Integer sourceAccountType, Integer targetAccountType, Long sourceUid, Long targetUid);

    boolean changeMoneyWithTimeout(BigDecimal money, Integer sourceAccountType, Integer targetAccountType, Long sourceUid, Long targetUid);
}
