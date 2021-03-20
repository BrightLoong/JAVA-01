package io.github.brightloong.account.common.api;

import io.github.brightloong.account.common.dto.AccountDTO;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.annotation.HmilyTCC;

/**
 * @author BrightLoong
 * @date 2021/3/15 21:07
 * @description
 */
public interface AccountService {

    @Hmily
    boolean changeMoney(AccountDTO accountDTO);

    @Hmily
    boolean changeMoneyWithConfirmTimeout(AccountDTO accountDTO);
}
