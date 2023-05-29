package by.bsuir.linguaserver.converter.impl;

import by.bsuir.linguaserver.converter.Converter;
import by.bsuir.linguaserver.dto.VideoContentEditFormDto;
import by.bsuir.linguaserver.entity.VideoContent;
import org.springframework.stereotype.Component;

@Component
public class VideoContentToVideoContentEditFormDtoConverter implements Converter<VideoContent, VideoContentEditFormDto> {

    @Override
    public VideoContentEditFormDto convert(VideoContent object) {
        var result = new VideoContentEditFormDto();
        result.setId(object.getId());
        result.setName(object.getName());
        result.setShortDescription(object.getShortDescription());
        result.setDescription(object.getDescription());
        result.setDuration(object.getDuration());
        result.setGenres(object.getGenres());
        return result;
    }
}
