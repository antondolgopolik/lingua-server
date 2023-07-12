package by.bsuir.linguaserver.job;

import by.bsuir.linguaserver.entity.Subtitle;
import by.bsuir.linguaserver.entity.VideoContent;
import by.bsuir.linguaserver.entity.VideoContentLoc;
import by.bsuir.linguaserver.entity.VideoContentLocWord;
import by.bsuir.linguaserver.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class VideoContentImportJob {

    private static final String IMPORT_PATH = "/home/anton/videocontent_import/main";
    private static final String RESOURCES_PATH = "/home/anton/nginx/lingua";
    private static final String VIDEO_CONTENT_PATH = RESOURCES_PATH + "/video-content";
    private static final String IMAGES_PATH = RESOURCES_PATH + "/img";
    private static final String SUBS_PATH = RESOURCES_PATH + "/sub";
    private static final String INTERPRETER_PATH = "/home/anton/PycharmProjects/sub-sync/venv/bin/python";
    private static final String PREPARE_WORDS_SCRIPT_PATH = "/home/anton/PycharmProjects/sub-sync/src/prepare_words.py";
    private static final String SYNC_SUBS_SCRIPT_PATH = "/home/anton/PycharmProjects/sub-sync/src/sync_subs.py";

    private final VideoContentRepository videoContentRepository;
    private final VideoContentLocRepository videoContentLocRepository;
    private final VideoContentLocWordRepository videoContentLocWordRepository;
    private final SubtitleRepository subtitleRepository;
    private final LanguageRepository languageRepository;
    private final GenreRepository genreRepository;

    public VideoContentImportJob(VideoContentRepository videoContentRepository, VideoContentLocRepository videoContentLocRepository, VideoContentLocWordRepository videoContentLocWordRepository, SubtitleRepository subtitleRepository, LanguageRepository languageRepository, GenreRepository genreRepository) {
        this.videoContentRepository = videoContentRepository;
        this.videoContentLocRepository = videoContentLocRepository;
        this.videoContentLocWordRepository = videoContentLocWordRepository;
        this.subtitleRepository = subtitleRepository;
        this.languageRepository = languageRepository;
        this.genreRepository = genreRepository;
    }

    @Scheduled(fixedDelay = 60000)
    public void importVideoContent() {
        Path importPath = Path.of(IMPORT_PATH);
        try (var dirStream = Files.newDirectoryStream(importPath, Files::isDirectory)) {
            for (Path dir : dirStream) {
                log.info("processing " + dir);
                importVideoContent(dir);
                FileSystemUtils.deleteRecursively(dir);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void importVideoContent(Path dir) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            VideoContentImportData data = objectMapper.readValue(dir.resolve("data.json").toFile(), VideoContentImportData.class);
            importVideoContent(dir, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void importVideoContent(Path dir, VideoContentImportData data) {
        UUID uuid = UUID.randomUUID();
        preparePosterFile(dir, uuid);

        VideoContent videoContent = new VideoContent();
        videoContent.setId(uuid);
        videoContent.setName(data.getName());
        videoContent.setShortDescription(data.getShortDescription());
        videoContent.setDescription(data.getDescription());
        videoContent.setDuration(data.getDuration());
        videoContent.setViews(0L);
        videoContent.setPrevMonthViews(0L);
        videoContent.setGenres(data.getGenres().stream().map(name -> genreRepository.findByName(name).orElseThrow()).toList());
        videoContent = videoContentRepository.save(videoContent);
        videoContent.setVideoContentLocs(importVideoContentLocs(dir, videoContent, data.getVideoContentLocs()));
        videoContentRepository.save(videoContent);
    }

    private void preparePosterFile(Path dir, UUID uuid) {
        try {
            Files.move(dir.resolve("poster"), Path.of(IMAGES_PATH, uuid.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<VideoContentLoc> importVideoContentLocs(Path dir, VideoContent videoContent, List<String> languages) {
        List<VideoContentLoc> videoContentLocs = new ArrayList<>();
        for (String langTag1 : languages) {
            videoContentLocs.add(importVideoContentLoc(dir, videoContent, languages, langTag1));
        }
        return videoContentLocs;
    }

    private VideoContentLoc importVideoContentLoc(Path dir, VideoContent videoContent, List<String> languages, String langTag1) {
        UUID uuid = UUID.randomUUID();
        prepareVideoFile(dir, uuid, langTag1);

        VideoContentLoc videoContentLoc = new VideoContentLoc();
        videoContentLoc.setId(uuid);
        videoContentLoc.setVideoContent(videoContent);
        videoContentLoc.setLanguage(languageRepository.findByTag(langTag1).orElseThrow());
        videoContentLoc.setSubtitles(importSubtitles(dir, languages, langTag1));
        videoContentLoc = videoContentLocRepository.save(videoContentLoc);
        videoContentLoc.setVideoContentLocWords(importVideoContentLocWords(dir, videoContentLoc, langTag1));
        return videoContentLocRepository.save(videoContentLoc);
    }

    private void prepareVideoFile(Path dir, UUID uuid, String langTag1) {
        try {
            Files.move(dir.resolve("video_" + langTag1), Path.of(VIDEO_CONTENT_PATH, uuid.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<Subtitle> importSubtitles(Path dir, List<String> languages, String langTag1) {
        List<Subtitle> subtitles = new ArrayList<>();
        for (String langTag2 : languages) {
            if (!langTag1.equals(langTag2)) {
                subtitles.add(importSubtitle(dir, langTag1, langTag2));
            }
        }
        return subtitles;
    }

    private Subtitle importSubtitle(Path dir, String langTag1, String langTag2) {
        UUID uuid = UUID.randomUUID();
        prepareSubtitleFile(dir, uuid, langTag1, langTag2);

        Subtitle subtitle = new Subtitle();
        subtitle.setId(uuid);
        subtitle.setSecondLanguage(languageRepository.findByTag(langTag2).orElseThrow());
        return subtitleRepository.save(subtitle);
    }

    private void prepareSubtitleFile(Path dir, UUID uuid, String langTag1, String langTag2) {
        Path subs_path1 = dir.resolve(langTag1 + ".srt");
        Path subs_path2 = dir.resolve(langTag2 + ".srt");
        Path output_path = Path.of(SUBS_PATH, uuid.toString());
        String command = INTERPRETER_PATH + ' ' +
                SYNC_SUBS_SCRIPT_PATH + ' ' +
                subs_path1 + ' ' +
                subs_path2 + ' ' +
                output_path;
        CommandLine commandLine = CommandLine.parse(command);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream, errStream);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        try {
            executor.execute(commandLine);
        } catch (IOException e) {
            log.error(errStream.toString());
            throw new RuntimeException(e);
        }
    }

    private Collection<VideoContentLocWord> importVideoContentLocWords(Path dir, VideoContentLoc videoContentLoc, String langTag1) {
        Collection<VideoContentLocWord> videoContentLocWords = new ArrayList<>();
        String output = runPrepareWordsProcess(dir, langTag1);
        String[] data = output.split("\n");
        Pattern pattern = Pattern.compile("^(.+) (\\d+)$");
        for (String record : data) {
            Matcher matcher = pattern.matcher(record);
            if (matcher.find()) {
                String word = matcher.group(1);
                Integer occurrence = Integer.parseInt(matcher.group(2));
                videoContentLocWords.add(importVideoContentLocWord(videoContentLoc, word, occurrence));
            }
        }
        return videoContentLocWords;
    }

    private String runPrepareWordsProcess(Path dir, String langTag1) {
        String command = INTERPRETER_PATH + ' ' +
                PREPARE_WORDS_SCRIPT_PATH + ' ' +
                langTag1 + ' ' +
                dir.resolve(langTag1 + ".srt");
        CommandLine commandLine = CommandLine.parse(command);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream, errStream);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        try {
            executor.execute(commandLine);
            return outputStream.toString();
        } catch (IOException e) {
            log.error(errStream.toString());
            throw new RuntimeException(e);
        }
    }

    private VideoContentLocWord importVideoContentLocWord(VideoContentLoc videoContentLoc, String word, Integer occurrence) {
        VideoContentLocWord videoContentLocWord = new VideoContentLocWord();
        videoContentLocWord.setVideoContentLoc(videoContentLoc);
        videoContentLocWord.setWord(word);
        videoContentLocWord.setOccurrence(occurrence);
        return videoContentLocWordRepository.save(videoContentLocWord);
    }
}
