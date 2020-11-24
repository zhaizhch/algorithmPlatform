package com.ehl.demo.controller;

import com.ehl.demo.entity.Task;
import com.ehl.demo.entity.TaskDto;
import com.ehl.demo.service.TaskService;
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

@Api(value = "任务管理", tags = "任务管理接口")
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @ApiOperation(value = "插入测试")
    @PostMapping(value = "/insert")
    public int taskInsert(HttpServletRequest request,
                              @ApiParam(value = "用户信息", required = true)
                              @RequestBody TaskDto taskDto){
        int ret = taskService.insertTest(taskDto);
        System.out.println("翟志成");
        System.out.println(ret);
        return ret;
    }

    @ApiOperation(value = "查询测试")
    @PostMapping(value = "/query")
    public Task taskQuery(HttpServletRequest request,
                         @ApiParam(value = "用户信息", required = true)
                         @RequestBody TaskDto taskDto){
        Task task=taskService.queryTask(taskDto);
        System.out.println("翟志成");
        System.out.println(task);
        return task;
    }

    @ApiOperation(value = "更新测试")
    @PostMapping(value = "/update")
    public int taskUpdate(HttpServletRequest request,
                          @ApiParam(value = "用户信息", required = true)
                          @RequestBody TaskDto taskDto){
        int ret=taskService.updateTask(taskDto);
        System.out.println("翟志成");
        System.out.println(ret);
        return ret;
    }

    @ApiOperation(value = "删除测试")
    @PostMapping(value = "/delete")
    public int taskDelete(HttpServletRequest request,
                          @ApiParam(value = "用户信息", required = true)
                          @RequestBody TaskDto taskDto){
        int ret=taskService.deleteTask(taskDto);
        System.out.println("翟志成");
        System.out.println(ret);
        return ret;
    }

    @ApiOperation(value = "id查询测试")
    @PostMapping(value = "/queryTaskIds")
    public int taskIdsQuery(HttpServletRequest request,
                            @ApiParam(value = "用户信息", required = true)
                            @RequestBody TaskDto taskDto){
        List<String> taskIdsList=taskService.queryTaskIds(taskDto);
        System.out.println("翟志成");
        System.out.println(taskIdsList);
        return taskIdsList.size();
    }
}
