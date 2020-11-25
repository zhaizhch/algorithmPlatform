package com.ehl.demo.controller;

import com.ehl.demo.common.RestfulEntity;
import com.ehl.demo.entity.Image;
import com.ehl.demo.entity.ImageDto;
import com.ehl.demo.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSONObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "镜像管理", tags = "镜像管理接口")
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @ApiOperation(value = "镜像插入")
    @PostMapping(value = "/insert")
    public RestfulEntity<JSONObject> insertTest(@ApiParam(value = "用户信息", required = true)
                              @RequestBody @Validated(ImageDto.addGroup.class) ImageDto imageDto){
        RestfulEntity<JSONObject> ret = imageService.insertTest(imageDto);
        return ret;
    }

    @ApiOperation(value = "条件查询")
    @PostMapping(value = "/queryByCondition")
    public RestfulEntity<JSONObject> queryImageByConditionTest(@ApiParam(value = "用户信息", required = true)
                     @RequestBody ImageDto imageDto) {
        List<Image>imageList = imageService.queryImageByConditionTest(imageDto);
        JSONObject result = new JSONObject();

        if(imageList.size()==0){
            result.put("data","no such results");
        }else{
            result.put("data",imageList);
        }
        return RestfulEntity.getSuccess(result,"查询成功");

    }

    @ApiOperation(value = "镜像更新")
    @PostMapping(value = "/updateTest")
    public RestfulEntity<JSONObject> updateTest(@ApiParam(value = "用户信息", required = true)
                          @RequestBody  @Validated(ImageDto.updateGroup.class) ImageDto imageDto){
        RestfulEntity<JSONObject>ret= imageService.updateImageInfoTest(imageDto);
        return ret;
    }

    @ApiOperation(value = "镜像删除")
    @PostMapping(value = "/deleteTest")
    public RestfulEntity<JSONObject> deleteTest(@ApiParam(value = "用户信息", required = true)
                          @RequestBody @Validated(ImageDto.deleteImageGroup.class) ImageDto imageDto){
        RestfulEntity<JSONObject>ret=imageService.deleteTest(imageDto);
        return ret;
    }

}
