package io.github.brightloong.sharding.tkmapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.additional.idlist.IdListProvider;
import tk.mybatis.mapper.common.IdsMapper;

import java.util.List;

/**
 * @author BrightLoong
 * @date 2021/3/5 23:27
 * @description
 */
public interface MyIdsMapper<T> extends IdsMapper<T> {
    @SelectProvider(
            type = IdListProvider.class,
            method = "dynamicSQL"
    )
    List<T> selectByIdList(@Param("idList") List<?> var1);
}
