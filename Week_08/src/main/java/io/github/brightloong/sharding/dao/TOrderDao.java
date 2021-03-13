package io.github.brightloong.sharding.dao;

import io.github.brightloong.sharding.domain.pojo.TOrder;
import io.github.brightloong.sharding.tkmapper.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public interface TOrderDao extends GenericDao<TOrder> {
}