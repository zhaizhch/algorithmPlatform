package com.ehl.demo.service;

import com.ehl.demo.entity.Container;
import com.ehl.demo.entity.ContainerDto;
import com.ehl.demo.mapper.ContainerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContainerService {
    @Autowired
    ContainerMapper containerMapper;

    public int insertTest(ContainerDto containerDto) {
        int ret = containerMapper.insertContainerInfo(containerDto);
        return ret;
    }

    public List<Container> queryContainer(ContainerDto containerDto) {
        List<Container> containerList = containerMapper.queryContainer(containerDto);
        return containerList;
    }

    public int updateTest(ContainerDto containerDto) {
        int ret = containerMapper.updateContainer(containerDto);
        return ret;
    }

    public int deleteTest(ContainerDto containerDto) {
        int ret = containerMapper.deleteContainer(containerDto);
        return ret;
    }


}
