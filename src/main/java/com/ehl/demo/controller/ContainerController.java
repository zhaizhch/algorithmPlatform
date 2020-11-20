package com.ehl.demo.controller;

import com.ehl.demo.entity.Container;
import com.ehl.demo.entity.ContainerDto;
import com.ehl.demo.service.ContainerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "用户管理", tags = "用户管理接口")
@RestController
@RequestMapping("/container")
public class ContainerController {
    @Autowired
    ContainerService containerService;

    @ApiOperation(value = "插入测试")
    @PostMapping(value = "/insert")
    public int insertTest(HttpServletRequest request,
                          @ApiParam(value = "用户信息", required = true)
                          @RequestBody ContainerDto containerDto) {

        int ret=containerService.insertTest(containerDto);
        return ret;
    }

    @ApiOperation(value = "查询测试")
    @PostMapping(value = "/query")
    public int queryTest(HttpServletRequest request,
                         @ApiParam(value = "用户信息", required = true)
                         @RequestBody ContainerDto containerDto){

        List<Container> containerList=containerService.queryContainer(containerDto);
        System.out.println("翟志成");
        System.out.println(containerList);
        return containerList.size();
    }

    @ApiOperation(value = "更新测试")
    @PostMapping(value = "/update")
    public int updayeTest(HttpServletRequest request,
                          @ApiParam(value = "用户信息", required = true)
                          @RequestBody ContainerDto containerDto){
        int ret=containerService.updateTest(containerDto);
        System.out.println("翟志成");
        System.out.println(ret);
        return ret;
    }

    @ApiOperation(value = "删除测试")
    @PostMapping(value = "/delete")
    public int deleteTest(HttpServletRequest request,
                          @ApiParam(value = "用户信息", required = true)
                          @RequestBody ContainerDto containerDto){
        int ret=containerService.deleteTest(containerDto);
        System.out.println("翟志成");
        System.out.println(ret);
        return ret;
    }

}
