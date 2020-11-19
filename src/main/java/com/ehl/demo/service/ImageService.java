package com.ehl.demo.service;

import com.ehl.demo.entity.Image;
import com.ehl.demo.entity.ImageDto;
import com.ehl.demo.mapper.ImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ImageService {
    @Autowired
    ImageMapper imageMapper;

    public int insertTest(ImageDto imageDto){
        int ret=imageMapper.insertImageInfo(imageDto);
        return ret;
    }

    public List<Image> queryImageTest(ImageDto imageDto){
            List<Image>imageList=imageMapper.queryImage(imageDto);
            return imageList;
    }
    public List<Image> queryImageByConditionTest(ImageDto imageDto){
        List<Image>imageList=imageMapper.queryImageByCondition(imageDto);
        return imageList;
    }
    public int updateImageInfoTest(ImageDto imageDto){
        int ret=imageMapper.updateImageInfo(imageDto);
        return ret;
    }
    public List<Image> queryDependencyTest(ImageDto imageDto){
        List<Image>imageList=imageMapper.queryDependencyImage(imageDto);
        return imageList;
    }
    public int deleteTest(ImageDto imageDto){
        int ret=imageMapper.deleteImage(imageDto);
        return ret;
    }

}
