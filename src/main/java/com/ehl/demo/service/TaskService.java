package com.ehl.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.ehl.demo.common.DisplayErrorCode;
import com.ehl.demo.common.RestfulEntity;
import com.ehl.demo.entity.*;
import com.ehl.demo.mapper.ContainerMapper;
import com.ehl.demo.mapper.ImageMapper;
import com.ehl.demo.mapper.TaskMapper;
import com.ehl.demo.utils.CommonUtils;
import com.ehl.demo.utils.FormatCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;

@Service
@Transactional
public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private ContainerMapper containerMapper;
    @Autowired
    private FormatCheck formatCheck;
    @Autowired
    private ImageMapper imageMapper;

    public RestfulEntity<JSONObject> saveToTask(TaskSaveDto taskSaveDto){
        if(taskSaveDto.getNamespace()==null||taskSaveDto.getNamespace().equals("")){
            taskSaveDto.setNamespace(taskSaveDto.getUserResult().getNamespace());
        }
        //判断输入合法性
        RestfulEntity<JSONObject>inputValidCheck=taskSaveDtoCheck(taskSaveDto);
        if(!inputValidCheck.getStatus().equals("0")){
            return inputValidCheck;
        }
        //判断任务名称是否被占用
        TaskDto taskDto=new TaskDto();
        taskDto.setNamespace(taskSaveDto.getNamespace());
        taskDto.setTaskName(taskSaveDto.getTaskName());
        Task task=taskMapper.queryTask(taskDto);
        if(task!=null){
            return RestfulEntity.getFailure(DisplayErrorCode.taskCheck);
        }
        //task和container插入到数据库中
        RestfulEntity<JSONObject>result=taskSave(taskSaveDto);

        return result;
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

    //检验输入taskSaveDto的有效性
    private RestfulEntity<JSONObject> taskSaveDtoCheck(TaskSaveDto taskSaveDto){
        if(!formatCheck.imageNameTagCheck(taskSaveDto.getImageName())){
            return RestfulEntity.getFailure(DisplayErrorCode.imageTagCheck);
        }
        if(!formatCheck.taskNameCheck(taskSaveDto.getTaskName())){
            return RestfulEntity.getFailure(DisplayErrorCode.taskNameCheck);
        }
        if(!formatCheck.taskTypeCheck(taskSaveDto.getTaskType())){
            return RestfulEntity.getFailure(DisplayErrorCode.taskTypeCheck);
        }
        if(!formatCheck.algoTypeCheck(taskSaveDto.getAlgoType())){
            return RestfulEntity.getFailure(DisplayErrorCode.algoTypeCheck);
        }
        if(!formatCheck.numberStartTypeCheck(taskSaveDto.getStartType())){
            return RestfulEntity.getFailure(DisplayErrorCode.getStartTypeCheck);
        }
        if(!formatCheck.hostAliasesCheck(taskSaveDto.getHostAliases())){
            return RestfulEntity.getFailure(DisplayErrorCode.hostAliasesCheck);
        }
        return RestfulEntity.getSuccess("输入有效");
    }

    //task和container插入数据库
    private RestfulEntity<JSONObject> taskSave(TaskSaveDto taskSaveDto){
        //task初始化
        TaskDto taskDto=new TaskDto();
        taskDto.setDeleteFlag("0");
        String taskId=CommonUtils.getRandomStr();
        taskDto.setTaskId(taskId);
        taskDto.setTaskName(taskSaveDto.getTaskName());
        taskDto.setNamespace(taskSaveDto.getNamespace());
        taskDto.setReplicas(taskSaveDto.getReplicas());
        taskDto.setGpuModel("1");
        taskDto.setCreateUserId(taskSaveDto.getCreateUserId());
        taskDto.setSvcIp(null);
        taskDto.setTaskType(taskSaveDto.getTaskType());
        taskDto.setAlgoType(taskSaveDto.getAlgoType());
        Date date=new Date();
        taskDto.setTaskCreateTime(date);
        taskDto.setRealtimeStream(0);
        taskDto.setVideoFile(0);
        //判断镜像是否被注册
        String[] imageNameTag=taskSaveDto.getImageName().split("\\:");
        ImageDto imageDto=new ImageDto();
        imageDto.setImageName(imageNameTag[0]);
        imageDto.setImageTag(imageNameTag[1]);
        List<Image> imageList=imageMapper.queryImage(imageDto);
        if(imageList.size()==0||imageList==null){
            return RestfulEntity.getFailure(DisplayErrorCode.imageCheck);
        }
        Image image=imageList.get(0);
        //container初始化
        ContainerDto containerDto=new ContainerDto();
        containerDto.setConId(CommonUtils.getRandomStr());
        containerDto.setTaskId(taskId);
        containerDto.setImageId(image.getImageId());
        containerDto.setCommand(taskSaveDto.getCommand());
        containerDto.setInput(taskSaveDto.getInput());
        containerDto.setOutput(taskSaveDto.getOutput());
        containerDto.setChannelId(taskSaveDto.getChannelId());
        containerDto.setDeleteFlag("0");
        containerDto.setTvChannel(taskSaveDto.getTvChannel());
        containerDto.setNamespace(taskSaveDto.getNamespace());
        containerDto.setHostAliases(taskSaveDto.getHostAliases());
        if(taskSaveDto.getArgs()==null||taskSaveDto.getArgs().equals("")){
            taskSaveDto.setArgs(image.getArgs());
        }
        containerDto.setArgs(taskSaveDto.getArgs());
        if(taskSaveDto.getEnv()==null||taskSaveDto.getEnv().equals("")){
            taskSaveDto.setEnv(image.getEnv());
        }
        containerDto.setEnv(taskSaveDto.getEnv());
        if(taskSaveDto.getCpuRequests()==null){
            if(image.getCpuRequests()==null){
                containerDto.setCpuRequests(500);
            }else{
                containerDto.setCpuRequests(image.getCpuRequests());
            }
        }
        if(taskSaveDto.getGpuRequests()==null){
            if(image.getGpuRequests()==null){
                containerDto.setGpuRequests(0);
            }else{
                containerDto.setGpuRequests(image.getGpuRequests());
            }
        }
        if(taskSaveDto.getMemRequests()==null){
            if(image.getMemRequests()==null){
                containerDto.setMemRequests(1024);
            }else{
                containerDto.setMemRequests(image.getMemRequests());
            }
        }
        //保存task
        try{
            taskMapper.insertTaskInfo(taskDto);
            containerMapper.insertContainerInfo(containerDto);
            return RestfulEntity.getSuccess("taskDto、containerDto插入成功");
        }catch(Exception e) {
            logger.error(e.getMessage(), e);
            logger.info("插入数据失败, insertTaskInfoList -> TaskDto = " + taskDto);
            logger.info("插入数据失败, insertcontainerInfoList -> ContainerDto = " + containerDto);
            return RestfulEntity.getFailure(504, "task、container插入失败");
        }
    }
}
