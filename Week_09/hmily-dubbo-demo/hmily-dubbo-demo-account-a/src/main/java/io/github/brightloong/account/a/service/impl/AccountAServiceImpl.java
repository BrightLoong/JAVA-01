package io.github.brightloong.account.a.service.impl;

import io.github.brightloong.account.a.service.AccountAService;
import io.github.brightloong.account.a.service.ChangeService;
import io.github.brightloong.account.common.constant.CommonConstants;
import io.github.brightloong.account.common.dto.AccountDTO;
import io.github.brightloong.account.common.mapper.AccountMapper;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author BrightLoong
 * @date 2021/3/16 21:34
 * @description
 */

@Service
public class AccountAServiceImpl implements AccountAService {

    @Autowired
    private ChangeService changeService;

    @Override
    public boolean changeMoney(BigDecimal money, Integer sourceAccountType, Integer targetAccountType, Long sourceUid, Long targetUid) {
       return changeService.changeMoney(buildSourceAccountDTO(money, sourceAccountType, targetAccountType, sourceUid, targetUid));
    }

    @Override
    @Transactional
    public boolean changeMoneyWithTimeout(BigDecimal money, Integer sourceAccountType, Integer targetAccountType, Long sourceUid, Long targetUid) {
        return changeService.changeMoneyWithConfirmTimeout(buildSourceAccountDTO(money, sourceAccountType, targetAccountType, sourceUid, targetUid));
    }


    private AccountDTO buildSourceAccountDTO(BigDecimal money, Integer sourceAccountType, Integer targetAccountType, Long sourceUid, Long targetUid) {
        BigDecimal ratio = CommonConstants.CHANGE_RATIO.get(sourceAccountType + "_" + targetAccountType);
        BigDecimal targetMoney = money.multiply(ratio);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(sourceUid);
        accountDTO.setTargeUid(targetUid);
        accountDTO.setSourceAccountType(sourceAccountType);
        accountDTO.setTargetAccountType(targetAccountType);
        accountDTO.setSourceMoney(money);
        accountDTO.setTargetMoney(targetMoney);
        return accountDTO;
    }

}
