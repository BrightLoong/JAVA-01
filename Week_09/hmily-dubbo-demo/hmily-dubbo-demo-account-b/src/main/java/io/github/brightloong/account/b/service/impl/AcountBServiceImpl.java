package io.github.brightloong.account.b.service.impl;

import io.github.brightloong.account.common.api.AccountService;
import io.github.brightloong.account.common.dto.AccountDTO;
import io.github.brightloong.account.common.mapper.AccountMapper;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author BrightLoong
 * @date 2021/3/16 22:06
 * @description
 */

@Service("acountBService")
public class AcountBServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean changeMoney(AccountDTO accountDTO) {
        int i = accountMapper.freezeMoney(accountDTO);
        if (i < 1) {
            throw new HmilyRuntimeException("账户异常，余额不足");
        }
        return true;
    }


    @Override
    @HmilyTCC(confirmMethod = "confirmTimeout", cancelMethod = "cancel")
    @Transactional
    public boolean changeMoneyWithConfirmTimeout(AccountDTO accountDTO) {
        int i = accountMapper.freezeMoney(accountDTO);
        if (i < 1) {
            throw new HmilyRuntimeException("账户异常，余额不足");
        }
        return true;
    }

    @Transactional
    public boolean confirmTimeout(AccountDTO accountDTO) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        accountMapper.confirmFreezeMoney(accountDTO);
        accountMapper.confirmExchange(accountDTO);
        return true;
    }

    public boolean cancel(AccountDTO accountDTO) {
        accountMapper.cancelFreezeMoney(accountDTO);
        return true;
    }

    public boolean confirm(AccountDTO accountDTO) {
        accountMapper.confirmFreezeMoney(accountDTO);
        accountMapper.confirmExchange(accountDTO);
        return true;
    }
}
