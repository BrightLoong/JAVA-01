package io.github.brightloong.mysql.tkmapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author BrightLoong
 * @date 2021/3/5 23:27
 * @description
 */

public interface GenericDao<T> extends Mapper<T>, MySqlMapper<T>, MyIdsMapper<T> {
}
