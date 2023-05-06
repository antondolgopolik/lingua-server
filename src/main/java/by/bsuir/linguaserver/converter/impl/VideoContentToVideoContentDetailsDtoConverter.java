package by.bsuir.linguaserver.converter.impl;

import by.bsuir.linguaserver.converter.Converter;
import by.bsuir.linguaserver.dto.VideoContentDetailsDto;
import by.bsuir.linguaserver.entity.VideoContent;
import by.bsuir.linguaserver.entity.VideoContentLoc;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class VideoContentToVideoContentDetailsDtoConverter implements Converter<VideoContent, VideoContentDetailsDto> {

    @Override
    public VideoContentDetailsDto convert(VideoContent object) {
        var result = new VideoContentDetailsDto();
        result.setId(object.getId());
        result.setName(object.getName());
        result.setDescription(object.getDescription());
        result.setDuration(object.getDuration());
        result.setViews(object.getViews());
        result.setGenres(object.getGenres());
        result.setVideoContentLocDtos(getVideoContentLocDtos(object));
        return result;
    }

    private Collection<VideoContentDetailsDto.VideoContentLocDto> getVideoContentLocDtos(VideoContent object) {
        return object.getVideoContentLocs().stream()
                .map(videoContentLoc -> {
                    var result = new VideoContentDetailsDto.VideoContentLocDto();
                    result.setId(videoContentLoc.getId());
                    result.setLanguage(videoContentLoc.getLanguage());
                    result.setSubtitles(videoContentLoc.getSubtitles());
                    return result;
                })
                .toList();
    }
}
