package io.github.brightloong.mysql.service.impl;

import io.github.brightloong.bright.datasource.annotation.BrightDs;
import io.github.brightloong.mysql.dao.DataTestDao;
import io.github.brightloong.mysql.domain.pojo.DataTest;
import io.github.brightloong.mysql.service.IWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author BrightLoong
 * @date 2021/3/6 10:18
 * @description
 */
@Service
public class DataWriteServiceImpl implements IWriteService<DataTest> {

    @Autowired
    private DataTestDao dataTestDao;

    @Override
    @BrightDs(value = "ds1")
    public void write(DataTest dataTest) {
        dataTestDao.insertSelective(dataTest);
    }

    @Override
    @BrightDs(value = "ds3")
    public void writeDs3(DataTest dataTest) {
        dataTestDao.insertSelective(dataTest);
    }

    @Override
    @BrightDs(value = "ds2")
    public void writeDs2(DataTest dataTest) {
        dataTestDao.insertSelective(dataTest);
    }
}
