package by.bsuir.linguaserver.converter.impl;

import by.bsuir.linguaserver.converter.Converter;
import by.bsuir.linguaserver.dto.VideoContentDetailsDto;
import by.bsuir.linguaserver.entity.Genre;
import by.bsuir.linguaserver.entity.VideoContent;
import org.springframework.stereotype.Component;

@Component
public class VideoContentToVideoContentDetailsDtoConverter implements Converter<VideoContent, VideoContentDetailsDto> {

    @Override
    public VideoContentDetailsDto convert(VideoContent object) {
        VideoContentDetailsDto videoContentDetailsDto = new VideoContentDetailsDto();
        videoContentDetailsDto.setId(object.getId());
        videoContentDetailsDto.setName(object.getName());
        videoContentDetailsDto.setDescription(object.getDescription());
        videoContentDetailsDto.setDuration(object.getDuration());
        videoContentDetailsDto.setViews(object.getViews());
        videoContentDetailsDto.setGenres(object.getGenres());
        videoContentDetailsDto.setVideoContentLocs(object.getVideoContentLocs());
        return videoContentDetailsDto;
    }
}
