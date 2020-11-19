package com.ehl.demo.mapper;

import com.ehl.demo.entity.Image;
import com.ehl.demo.entity.ImageDto;
import com.ehl.demo.entity.Task;
import com.ehl.demo.entity.TaskDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author User
 */
@Mapper
@Component
public interface TaskMapper {
    Task queryTask(TaskDto taskDto);
    int insertTaskInfo(TaskDto taskDto);
    int updateTask(TaskDto taskDto);
    int deleteTask(TaskDto taskDto);
    List<String> queryTaskIds(TaskDto taskDto);
}
