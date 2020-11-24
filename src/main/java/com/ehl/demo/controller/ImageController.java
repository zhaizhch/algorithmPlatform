package com.ehl.demo.controller;

import com.ehl.demo.common.DisplayErrorCode;
import com.ehl.demo.common.RestfulEntity;
import com.ehl.demo.entity.Image;
import com.ehl.demo.entity.ImageDto;
import com.ehl.demo.entity.UserDto;
import com.ehl.demo.service.ImageService;
import com.ehl.demo.utils.CommonUtils;
import com.ehl.demo.utils.FormatCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "镜像管理", tags = "镜像测试接口")
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;
    FormatCheck formatCheck;

    @ApiOperation(value = "插入测试")
    @PostMapping(value = "/insert")
    public RestfulEntity<JSONObject> insertTest(@ApiParam(value = "用户信息", required = true)
                              @RequestBody @Validated(ImageDto.addGroup.class) ImageDto imageDto){
        /*if (!formatCheck.imageNameCheck(imageDto.getImageName())) {
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
        if(imageDto.getAlgoType()=="0"){
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
        List<Image> uniqueCheck=imageService.queryImageTest(imageDto);
        if(uniqueCheck.size()!=0){
            return RestfulEntity.getFailure(DisplayErrorCode.uniqCheck);
        }
        String imageId = CommonUtils.getRandomStr();
        imageDto.setImageId(imageId);
        imageDto.setDeleteFlag("0");
        if(imageDto.getDependencyServiceId()!=null&&imageDto.getAlgoType()=="0"){
            String[] dependencyServiceIdSplit=imageDto.getDependencyServiceId().split("\\,");
            ArrayList<String> dependencyServiceIdResult=new ArrayList<>();
            for(String each:dependencyServiceIdSplit){
                String[] names=each.split("\\:");
                ImageDto imageDto1=new ImageDto();
                imageDto1.setImageName(names[1]);
                imageDto1.setImageTag(names[2]);
                List<Image> dependencyServiceIdCheck=imageService.queryImageTest(imageDto1);
                if(dependencyServiceIdCheck==null || dependencyServiceIdCheck.size()==0){
                    return RestfulEntity.getFailure(DisplayErrorCode.dependencyAlgoCheck);
                }
                dependencyServiceIdResult.add(dependencyServiceIdCheck.get(0).getEventType().toString());
            }
            String dependencyServiceId=new String();
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
            imageDto.setDependencyServiceId(dependencyServiceId);
        }
        else{
            imageDto.setDependencyServiceId(null);
        }
        imageDto.setEventType(null);*/
        RestfulEntity<JSONObject> ret = imageService.insertTest(imageDto);
        return ret;
    }

    @ApiOperation(value = "查询测试")
    @PostMapping(value = "/queryImage")
    public int queryImageTest(@ApiParam(value = "用户信息", required = true)
                    @RequestBody ImageDto imageDto){
        List<Image>imageList = imageService.queryImageTest(imageDto);
        System.out.println("翟志成");
        System.out.println(imageList.size());
        return imageList.size();
    }
    @ApiOperation(value = "条件查询测试")
    @PostMapping(value = "/test")
    public RestfulEntity<JSONObject> queryImageByConditionTest(@ApiParam(value = "用户信息", required = true)
                     @RequestBody ImageDto imageDto) throws JSONException {
        List<Image>imageList = imageService.queryImageByConditionTest(imageDto);
        JSONObject result = new JSONObject();

        if(imageList.size()==0){
            result.put("data","no such results");
        }else{
            result.put("data",imageList);
        }
        return RestfulEntity.getSuccess(result,"查询成功");

    }
    @ApiOperation(value = "更新测试")
    @PostMapping(value = "/updateTest")
    public RestfulEntity<JSONObject> updateTest(@ApiParam(value = "用户信息", required = true)
                          @RequestBody  @Validated(ImageDto.updateGroup.class) ImageDto imageDto){
        /*if (!formatCheck.imageNameCheck(imageDto.getImageName())) {
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
        if(imageDto.getAlgoType()=="0"){
            if(imageDto.getFrameInterval()==null){
                return RestfulEntity.getFailure(502,"frameInterval不能为空");
            }
            if(imageDto.getFrameNumber()==null){
                return RestfulEntity.getFailure(503,"frameNumber不能为空");
            }
            if(!formatCheck.dependencyCheck(imageDto.getDependencyServiceId())){
                return RestfulEntity.getFailure(DisplayErrorCode.dependencyServiceIdCheck);
            }
        }*/
        /*ImageDto imageDto1=new ImageDto();
        imageDto1.setImageId(imageDto.getImageId());
        List<Image> updateSearchList=imageService.queryImageTest(imageDto1);
        if(updateSearchList.size()==0){
            return RestfulEntity.getFailure(DisplayErrorCode.imageIdErrCheck);
        }
        Image updateSearch=updateSearchList.get(0);
        if(updateSearch.getImageName()!=imageDto.getImageName()||updateSearch.getImageTag()!=imageDto.getImageTag()){
            return RestfulEntity.getFailure(DisplayErrorCode.updataImageNameTagCheck);
        }
        ImageDto imageDto2=new ImageDto();
        imageDto2.setImageName(imageDto.getImageName());
        imageDto2.setImageTag(imageDto.getImageTag());
        List<Image>uniqueCheck=imageService.queryImageTest(imageDto2);
        for(Image each:uniqueCheck){
            if(each.getImageId()!=imageDto.getImageId()){
                return RestfulEntity.getFailure(DisplayErrorCode.uniqueCheck);
            }
        }
        if(imageDto.getDependencyServiceId()!=""&&imageDto.getDependencyServiceId()!=null){
            String[] dependencyServiceIdSplit=imageDto.getDependencyServiceId().split("\\,");
            List<String> dependencyServiceIdResult=new ArrayList<>();
            for(String each:dependencyServiceIdSplit){
                String[] names=each.split("\\:");
                ImageDto imageDto3=new ImageDto();
                imageDto3.setImageName(names[0]);
                imageDto3.setImageTag(names[1]);
                List<Image> dependencyServiceIdCheckList=imageService.queryImageTest(imageDto3);
                if(dependencyServiceIdCheckList.size()==0||dependencyServiceIdCheckList==null){
                    return RestfulEntity.getFailure(DisplayErrorCode.dependencyAlgoCheck);
                }
                Image dependencyServiceIdCheck=dependencyServiceIdCheckList.get(0);
                dependencyServiceIdResult.add(dependencyServiceIdCheck.getEventType().toString());
            }
            String dependencyServiceId=new String();
            for(int i=0;i<dependencyServiceIdResult.size();i++) {
                if (i != dependencyServiceIdResult.size() - 1) {
                    dependencyServiceId += dependencyServiceIdResult.get(i);
                    dependencyServiceId += ",";
                } else {
                    dependencyServiceId += dependencyServiceIdResult.get(i);
                }
            }
            imageDto.setDependencyServiceId(dependencyServiceId);
        }
        else{
            imageDto.setDependencyServiceId(null);
        }*/
        RestfulEntity<JSONObject>ret= imageService.updateImageInfoTest(imageDto);
        return ret;
    }
    @ApiOperation(value = "依赖查询测试")
    @PostMapping(value = "/dependencyTest")
    public int dependencyImageTest(@ApiParam(value = "用户信息", required = true)
                                         @RequestBody ImageDto imageDto){
        List<Image>imageList = imageService.queryDependencyTest(imageDto);
        System.out.println("翟志成");
        System.out.println(imageList.size());
        return imageList.size();
    }
    @ApiOperation(value = "删除测试")
    @PostMapping(value = "/deleteTest")
    public RestfulEntity<JSONObject> deleteTest(@ApiParam(value = "用户信息", required = true)
                          @RequestBody @Validated(ImageDto.deleteGroup.class)ImageDto imageDto, UserDto userDto){

        /*ImageDto imageDto1=new ImageDto();
        imageDto1.setImageId(imageDto.getImageId());
        List<Image>deleteSearch=imageService.queryImageTest(imageDto1);
        if(deleteSearch.size()==0||deleteSearch==null){
            return RestfulEntity.getFailure(DisplayErrorCode.imageIdErrCheck);
        }else{
            ImageDto imageDto2=new ImageDto();
            imageDto2.setSearchCondition(imageDto.getSearchCondition());
            List<Image>dependencyCheck=imageService.queryDependencyTest(imageDto2);
            if(dependencyCheck.size()!=0||dependencyCheck!=null){
                return RestfulEntity.getFailure(DisplayErrorCode.imageDependencyErr);
            }
            ImageDto imageDto3=new ImageDto();
            imageDto3.setNamespace(imageDto.getNamespace());
            List<Image>imageList=imageService.queryImageByConditionTest(imageDto3);
            if(userDto.getAuthority()!=3&&(imageList.size()!=0||imageList.size()==0||imageList.get(0).getImageId()!=imageDto.getImageId())){
                return RestfulEntity.getFailure(DisplayErrorCode.authorityErr);
            }
        }*/
        RestfulEntity<JSONObject>ret=imageService.deleteTest(imageDto,userDto);
        return ret;
    }

}
