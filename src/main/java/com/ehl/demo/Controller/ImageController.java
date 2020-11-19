package com.ehl.demo.Controller;

import com.ehl.demo.entity.Image;
import com.ehl.demo.entity.ImageDto;
import com.ehl.demo.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "用户管理", tags = "用户管理接口")
@RestController
@RequestMapping("/user")
public class ImageController {

    @Autowired
    ImageService imageService;

    @ApiOperation(value = "插入测试")
    @PostMapping(value = "/insert")
    public int insertTest(HttpServletRequest request,
                              @ApiParam(value = "用户信息", required = true)
                              @RequestBody ImageDto imageDto){
        int ret = imageService.insertTest(imageDto);
        System.out.println("翟志成");
        System.out.println(ret);
        return 0;
    }

    @ApiOperation(value = "查询测试")
    @PostMapping(value = "/queryImage")
    public int queryImageTest(HttpServletRequest request,
                    @ApiParam(value = "用户信息", required = true)
                    @RequestBody ImageDto imageDto){
        List<Image>imageList = imageService.queryImageTest(imageDto);
        System.out.println("翟志成");
        System.out.println(imageList.size());
        return imageList.size();
    }
    @ApiOperation(value = "条件查询测试")
    @PostMapping(value = "/test")
    public int queryImageByConditionTest(HttpServletRequest request,
                     @ApiParam(value = "用户信息", required = true)
                     @RequestBody ImageDto imageDto){
        List<Image>imageList = imageService.queryImageByConditionTest(imageDto);
        System.out.println("翟志成");
        System.out.println(imageList.size());
        return 0;
    }
    @ApiOperation(value = "更新测试")
    @PostMapping(value = "/updateTest")
    public int updateTest(HttpServletRequest request,
                          @ApiParam(value = "用户信息", required = true)
                          @RequestBody ImageDto imageDto){

        int ret = imageService.updateImageInfoTest(imageDto);
        System.out.println("翟志成");
        System.out.println(ret);
        return 0;
    }
    @ApiOperation(value = "依赖查询测试")
    @PostMapping(value = "/dependencyTest")
    public int dependencyImageTest(HttpServletRequest request,
                                         @ApiParam(value = "用户信息", required = true)
                                         @RequestBody ImageDto imageDto){
        List<Image>imageList = imageService.queryDependencyTest(imageDto);
        System.out.println("翟志成");
        System.out.println(imageList.size());
        return 0;
    }
    @ApiOperation(value = "删除测试")
    @PostMapping(value = "/deleteTest")
    public int deleteTest(HttpServletRequest request,
                          @ApiParam(value = "用户信息", required = true)
                          @RequestBody @Validated(ImageDto.deleteGroup.class)ImageDto imageDto){

        int ret = imageService.deleteTest(imageDto);
        System.out.println("翟志成");
        System.out.println(ret);
        return 0;
    }

}
