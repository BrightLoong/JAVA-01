package io.github.brightloong.mysql.service;

/**
 * @author BrightLoong
 * @date 2021/3/6 10:17
 * @description
 */
public interface IWriteService<T> {
    void write(T t);

    void writeDs2(T t);

    void writeDs3(T t);
}
