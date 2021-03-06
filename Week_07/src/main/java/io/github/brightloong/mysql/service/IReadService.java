package io.github.brightloong.mysql.service;

import java.util.List;

/**
 * @author BrightLoong
 * @date 2021/3/6 10:17
 * @description
 */
public interface IReadService<T> {
    public List<T> readFromSlaves();

    public List<T> readFromDs2();

    List<T> readFromMain();
}
