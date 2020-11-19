package com.ehl.demo.mapper;

import com.ehl.demo.entity.Image;
import com.ehl.demo.entity.ImageDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author User
 */
@Mapper
@Component
public interface ImageMapper {
    int insertImageInfo(ImageDto imageDto);
    List<Image> queryImage(ImageDto imageDto);
    List<Image> queryImageByCondition(ImageDto imageDto);
    int updateImageInfo(ImageDto imageDto);
    List<Image>queryDependencyImage(ImageDto imageDto);
    int deleteImage(ImageDto imageDto);
}
