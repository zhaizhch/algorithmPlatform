package com.ehl.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.ehl.demo.common.DisplayErrorCode;
import com.ehl.demo.common.RestfulEntity;
import com.ehl.demo.entity.Image;
import com.ehl.demo.entity.ImageDto;
import com.ehl.demo.mapper.ImageMapper;
import com.ehl.demo.utils.CommonUtils;
import com.ehl.demo.utils.FormatCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ImageService {
    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);
    @Autowired
    ImageMapper imageMapper;
    @Autowired
    private FormatCheck formatCheck;

    public RestfulEntity<JSONObject> insertTest(ImageDto imageDto){
        //参数有效性校验
        RestfulEntity<JSONObject> dataValidationResult=dataValidation(imageDto);
        if(!(dataValidationResult.getStatus().equals("0"))){
            return dataValidationResult;
        }
        //参数唯一性校验
        List<Image> uniqueCheck=queryImageTest(imageDto);
        if(uniqueCheck.size()!=0){
            return RestfulEntity.getFailure(DisplayErrorCode.uniqCheck);
        }
        //参数初始化
        String imageId = CommonUtils.getRandomStr();
        imageDto.setImageId(imageId);
        imageDto.setDeleteFlag("0");
        imageDto.setEventType(null);
        //dependencyServiceId的生成
        RestfulEntity<JSONObject>dependencyServiceId=dependencyServiceIdInti(imageDto);
        if(!dependencyServiceId.getStatus().equals("0")){
            return dependencyServiceId;
        }else{
            imageDto.setDependencyServiceId(dependencyServiceId.getMsg());
        }
        //镜像插入
        try{
            imageMapper.insertImageInfo(imageDto);
            return RestfulEntity.getSuccess("注册成功");
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.info("插入数据失败, insertImageInfoList -> ImageDto = " + imageDto);
            return RestfulEntity.getFailure(504,"image插入失败");
        }
    }

    public List<Image> queryImageTest(ImageDto imageDto){
            List<Image>imageList=imageMapper.queryImage(imageDto);
            return imageList;
    }
    public List<Image> queryImageByConditionTest(ImageDto imageDto){
        List<Image>imageList=imageMapper.queryImageByCondition(imageDto);
        return imageList;
    }
    public RestfulEntity<JSONObject> updateImageInfoTest(ImageDto imageDto){
        //参数有效性校验
        RestfulEntity<JSONObject> dataValidationResult=dataValidation(imageDto);
        if(!(dataValidationResult.getStatus().equals("0"))){
            return dataValidationResult;
        }
        //镜像唯一性校验
        ImageDto imageDto1=new ImageDto();
        imageDto1.setImageId(imageDto.getImageId());
        List<Image> updateSearchList=queryImageTest(imageDto1);
        if(updateSearchList.size()==0){
            return RestfulEntity.getFailure(DisplayErrorCode.imageIdErrCheck);
        }
        //镜像算法和名字无法修改
        Image updateSearch=updateSearchList.get(0);
        if(!updateSearch.getImageName().equals(imageDto.getImageName())||!updateSearch.getImageTag().equals(imageDto.getImageTag())){
            return RestfulEntity.getFailure(DisplayErrorCode.updataImageNameTagCheck);
        }
        //镜像名字和标签可以唯一确定一个镜像，如果出现多个，则出现了脏数据
        ImageDto imageDto2=new ImageDto();
        imageDto2.setImageName(imageDto.getImageName());
        imageDto2.setImageTag(imageDto.getImageTag());
        List<Image>uniqueCheck=queryImageTest(imageDto2);
        for(Image each:uniqueCheck){
            if(!each.getImageId().equals(imageDto.getImageId())){
                return RestfulEntity.getFailure(DisplayErrorCode.uniqueCheck);
            }
        }
        //dependencyServiceId的获得
        RestfulEntity<JSONObject>dependencyServiceId=dependencyServiceIdInti(imageDto);
        if(!dependencyServiceId.getStatus().equals("0")){
            return dependencyServiceId;
        }else{
            imageDto.setDependencyServiceId(dependencyServiceId.getMsg());
        }
        //镜像更新
        try{
            imageMapper.updateImageInfo(imageDto);
            return RestfulEntity.getSuccess("更新成功");
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.info("更新镜像失败, updateImageInfoTest -> imageDto = " + imageDto);
            return RestfulEntity.getFailure(505,"image更新失败");
        }
    }
    public List<Image> queryDependencyTest(ImageDto imageDto){
        List<Image>imageList=imageMapper.queryDependencyImage(imageDto);
        return imageList;
    }
    public RestfulEntity<JSONObject> deleteTest(ImageDto imageDto){
        //查询要删除镜像是否存在
        ImageDto imageDto1=new ImageDto();
        imageDto1.setImageId(imageDto.getImageId());
        List<Image>deleteSearch=queryImageTest(imageDto1);
        if(deleteSearch.size()==0||deleteSearch==null){
            return RestfulEntity.getFailure(DisplayErrorCode.imageIdErrCheck);
        }else{
            //查询是否是被依赖镜像
            ImageDto imageDto2=new ImageDto();
            imageDto2.setEventType(imageDto.getEventType());
            List<Image>dependencyCheck=queryDependencyTest(imageDto2);
            if(dependencyCheck.size()!=0&&dependencyCheck!=null){
                return RestfulEntity.getFailure(DisplayErrorCode.imageDependencyErr);
            }
            //判断删除权限
            if(imageDto.getAuthority()!=3&&!(deleteSearch.get(0).getImageName().startsWith(imageDto.getNamespace()))){
                return RestfulEntity.getFailure(DisplayErrorCode.authorityErr);
            }
        }
        //镜像删除操作
        try{
            imageMapper.deleteImage(imageDto);
            return RestfulEntity.getSuccess("删除成功");
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.info("删除镜像失败, deleteImageInfoTest -> imageDto = " + imageDto);
            return RestfulEntity.getFailure(506,"image删除更新失败");
        }
    }

    //对输入有效性进行判断
    public RestfulEntity<JSONObject> dataValidation(ImageDto imageDto){
        if (!formatCheck.imageNameCheck(imageDto.getImageName())) {
            return RestfulEntity.getFailure(DisplayErrorCode.imageNameCheck);
        }
        if(!formatCheck.tagCheck(imageDto.getImageTag())){
            return RestfulEntity.getFailure(DisplayErrorCode.tagCheck);
        }
        if(!formatCheck.algoDescCheck(imageDto.getAlgoDesc())){
            return RestfulEntity.getFailure(DisplayErrorCode.formatCheck);
        }
        if(!formatCheck.imagePortsCheck(imageDto.getImagePorts())){
            return RestfulEntity.getFailure(DisplayErrorCode.portsCheck);
        }
        if(!formatCheck.imageMountCheck(imageDto.getImageMount())){
            return RestfulEntity.getFailure(DisplayErrorCode.imageMountCheck);
        }
        if(!formatCheck.imageConfigCheck(imageDto.getConfig())){
            return RestfulEntity.getFailure(DisplayErrorCode.configCheck);
        }
        if(!formatCheck.subPathMappingCheck(imageDto.getSubPath())){
            return RestfulEntity.getFailure(DisplayErrorCode.subPathCheck);
        }
        if(!formatCheck.hostPathMappingCheck(imageDto.getHostPath())){
            return RestfulEntity.getFailure(DisplayErrorCode.hostPathCheck);
        }
        if(!formatCheck.workingDirCheck(imageDto.getWorkingDir())){
            return RestfulEntity.getFailure(DisplayErrorCode.workingDirCheck);
        }
        if(!formatCheck.algoTypeCheck(imageDto.getAlgoType())){
            return RestfulEntity.getFailure(DisplayErrorCode.eventTypeCheck);
        }
        if(!formatCheck.envCheck(imageDto.getEnv())){
            return RestfulEntity.getFailure(DisplayErrorCode.envCheck);
        }
        if(imageDto.getAlgoType().equals("0")){
            if(imageDto.getFrameInterval()==null){
                return RestfulEntity.getFailure(502,"frameInterval不能为空");
            }
            if(imageDto.getFrameNumber()==null){
                return RestfulEntity.getFailure(503,"frameNumber不能为空");
            }
            if(!formatCheck.dependencyCheck(imageDto.getDependencyServiceId())){
                return RestfulEntity.getFailure(DisplayErrorCode.dependencyServiceIdCheck);
            }
        }
        return RestfulEntity.getSuccess("输入有效");
    }

    //生成dependencyServiceId
    public RestfulEntity<JSONObject> dependencyServiceIdInti(ImageDto imageDto){
        String dependencyServiceId=new String();
        //判断依赖镜像是否为空或算法类型是否为0  algoType：0：自研  1：生态  2：其他
        if(imageDto.getDependencyServiceId()!=null&&imageDto.getAlgoType().equals("0")){
            String[] dependencyServiceIdSplit=imageDto.getDependencyServiceId().split("\\,");
            ArrayList<String> dependencyServiceIdResult=new ArrayList<>();
            for(String each:dependencyServiceIdSplit){
                String[] names=each.split("\\:");
                ImageDto imageDto1=new ImageDto();
                imageDto1.setImageName(names[0]);
                imageDto1.setImageTag(names[1]);
                List<Image> dependencyServiceIdCheck=queryImageTest(imageDto1);
                if(dependencyServiceIdCheck==null || dependencyServiceIdCheck.size()==0){
                    return RestfulEntity.getFailure(DisplayErrorCode.dependencyAlgoCheck);
                }
                dependencyServiceIdResult.add(dependencyServiceIdCheck.get(0).getEventType().toString());
            }
            //将dependencyServiceId由List转换成String
            for(int i=0;i<dependencyServiceIdResult.size();i++)
            {
                if(i!=dependencyServiceIdResult.size()-1){
                    dependencyServiceId+=dependencyServiceIdResult.get(i);
                    dependencyServiceId+=",";
                }
                else{
                    dependencyServiceId+=dependencyServiceIdResult.get(i);
                }
            }
        }
        else{
            dependencyServiceId=null;
        }
        return RestfulEntity.getSuccess(dependencyServiceId);
    }

}
