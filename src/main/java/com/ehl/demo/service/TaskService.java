package com.ehl.demo.service;

import com.ehl.demo.entity.Task;
import com.ehl.demo.entity.TaskDto;
import com.ehl.demo.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {
    @Autowired
    TaskMapper taskMapper;

    public int insertTest(TaskDto taskDto){
        int ret = taskMapper.insertTaskInfo(taskDto);
        return ret;
    }

    public Task queryTask(TaskDto taskDto){
        Task task= taskMapper.queryTask(taskDto);
        return task;
    }

    public int updateTask(TaskDto taskDto){
        int ret=taskMapper.updateTask(taskDto);
        return ret;
    }

    public int deleteTask(TaskDto taskDto){
        int ret= taskMapper.deleteTask(taskDto);
        return ret;
    }

    public List<String> queryTaskIds(TaskDto taskDto){
        List<String> taskIdsList=taskMapper.queryTaskIds(taskDto);
        return taskIdsList;
    }


}
