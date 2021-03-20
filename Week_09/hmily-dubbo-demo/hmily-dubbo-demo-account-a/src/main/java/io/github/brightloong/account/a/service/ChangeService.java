package io.github.brightloong.account.a.service;

import io.github.brightloong.account.common.dto.AccountDTO;
import org.dromara.hmily.annotation.HmilyTCC;

/**
 * @author BrightLoong
 * @date 2021/3/16 22:03
 * @description
 */
public interface ChangeService {
    boolean changeMoney(AccountDTO accountDTO);

    boolean changeMoneyWithConfirmTimeout(AccountDTO accountDTO);
}
