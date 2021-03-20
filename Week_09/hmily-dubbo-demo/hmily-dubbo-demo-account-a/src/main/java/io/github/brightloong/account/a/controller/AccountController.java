package io.github.brightloong.account.a.controller;

import io.github.brightloong.account.a.service.AccountAService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author BrightLoong
 * @date 2021/3/16 22:27
 * @description
 */

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountAService accountAService;

    @PostMapping(value = "/changeMoney")
    @ApiOperation(value = "外汇")
    public String changeMoney(@RequestParam("money") BigDecimal money,
                              @RequestParam("sourceAccountType") Integer sourceAccountType,
                              @RequestParam("targetAccountType") Integer targetAccountType,
                              @RequestParam("sourceUid") Long sourceUid,
                              @RequestParam("targetUid") Long targetUid) {
        accountAService.changeMoney(money, sourceAccountType, targetAccountType, sourceUid, targetUid);
        return "success";
    }

    @PostMapping(value = "/changeMoneyWithConfirmTimeout")
    @ApiOperation(value = "外汇")
    public String changeMoneyWithConfirmTimeout(@RequestParam("money") BigDecimal money,
                              @RequestParam("sourceAccountType") Integer sourceAccountType,
                              @RequestParam("targetAccountType") Integer targetAccountType,
                              @RequestParam("sourceUid") Long sourceUid,
                              @RequestParam("targetUid") Long targetUid) {
        accountAService.changeMoneyWithTimeout(money, sourceAccountType, targetAccountType, sourceUid, targetUid);
        return "success";
    }
}
