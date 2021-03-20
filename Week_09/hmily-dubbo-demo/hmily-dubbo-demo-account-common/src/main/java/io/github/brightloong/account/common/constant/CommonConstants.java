package io.github.brightloong.account.common.constant;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author BrightLoong
 * @date 2021/3/15 21:14
 * @description
 */
public interface CommonConstants {
     Map<String, BigDecimal> CHANGE_RATIO = new HashMap<String, BigDecimal>() {
        {
            put("1_2", new BigDecimal("0.15")); //1人命币换0.15美元
            put("2_1", new BigDecimal("7")); // 1美元换7人命币
        }
    };
}
