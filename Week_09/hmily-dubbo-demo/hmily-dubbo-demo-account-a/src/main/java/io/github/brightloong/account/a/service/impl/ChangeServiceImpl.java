package io.github.brightloong.account.a.service.impl;

import io.github.brightloong.account.a.service.ChangeService;
import io.github.brightloong.account.common.api.AccountService;
import io.github.brightloong.account.common.dto.AccountDTO;
import io.github.brightloong.account.common.mapper.AccountMapper;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author BrightLoong
 * @date 2021/3/16 22:04
 * @description
 */
@Service
public class ChangeServiceImpl implements ChangeService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountService accountService;

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean changeMoney(AccountDTO accountDTO) {
        //冻结sourceUid的资产
        int i = accountMapper.freezeMoney(accountDTO);
        if (i < 1) {
            throw new HmilyRuntimeException("账户余额不足");
        }
        //调用用户targetUid的方法
        accountService.changeMoney(buildTargetAccountDTO(accountDTO));
        return true;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean changeMoneyWithConfirmTimeout(AccountDTO accountDTO) {
        //冻结sourceUid的资产
        int i = accountMapper.freezeMoney(accountDTO);
        if (i < 1) {
            throw new HmilyRuntimeException("账户余额不足");
        }
        //调用用户targetUid的方法
        accountService.changeMoneyWithConfirmTimeout(buildTargetAccountDTO(accountDTO));
        return true;
    }


    public void cancel(AccountDTO accountDTO) {
        accountMapper.cancelFreezeMoney(accountDTO);
    }


    public void confirm(AccountDTO accountDTO) {
        accountMapper.confirmFreezeMoney(accountDTO);
        accountMapper.confirmExchange(accountDTO);
    }

    private AccountDTO buildTargetAccountDTO(AccountDTO accountDTO) {
        AccountDTO targetAccountDTO = new AccountDTO();
        targetAccountDTO.setUserId(accountDTO.getTargeUid());
        targetAccountDTO.setSourceAccountType(accountDTO.getTargetAccountType());
        targetAccountDTO.setTargetAccountType(accountDTO.getSourceAccountType());
        targetAccountDTO.setSourceMoney(accountDTO.getTargetMoney());
        targetAccountDTO.setTargetMoney(accountDTO.getSourceMoney());
        return targetAccountDTO;
    }
}
