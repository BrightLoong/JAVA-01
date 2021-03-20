package io.github.brightloong.rpc.demo.provider;

import io.github.brightloong.rpc.demo.api.TestService;

/**
 * @author BrightLoong
 * @date 2021/3/20 18:02
 * @description
 */
public class TestServiceImpl implements TestService {
    @Override
    public String say(String word) {
        return "from remote say:" + word;
    }
}
