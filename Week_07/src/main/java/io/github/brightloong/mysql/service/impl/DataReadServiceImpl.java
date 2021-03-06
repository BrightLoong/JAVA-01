package io.github.brightloong.mysql.service.impl;

import io.github.brightloong.bright.datasource.annotation.BrightDs;
import io.github.brightloong.mysql.dao.DataTestDao;
import io.github.brightloong.mysql.domain.pojo.DataTest;
import io.github.brightloong.mysql.service.IReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BrightLoong
 * @date 2021/3/6 10:18
 * @description
 */

@Service
public class DataReadServiceImpl implements IReadService<DataTest> {

    @Autowired
    private DataTestDao dataTestDao;

    @Override
    @BrightDs(readOnly = true)
    public List<DataTest> readFromSlaves() {
        return dataTestDao.selectAll();
    }

    @Override
    @BrightDs(value = "ds2")
    public List<DataTest> readFromDs2() {
        return dataTestDao.selectAll();
    }

    @Override
    public List<DataTest> readFromMain() {
        return dataTestDao.selectAll();
    }

    public static void main(String[] args) {
        DataTest dataTest = new DataTest();
        dataTest.setDataSource("111");
        dataTest.setName("2222");
        List<DataTest> list = new ArrayList<>();
        list.add(dataTest);
        System.out.println("====>" + list);
    }
}
