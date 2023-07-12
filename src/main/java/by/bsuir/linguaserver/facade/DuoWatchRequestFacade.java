package by.bsuir.linguaserver.facade;

import by.bsuir.linguaserver.converter.impl.DuoWatchRequestPageToPersonalDuoWatchRequestPageDtoConverter;
import by.bsuir.linguaserver.converter.impl.DuoWatchRequestToDuoWatchRequestCatalogItemDtoConverter;
import by.bsuir.linguaserver.converter.impl.DuoWatchResponsePageToPersonalDuoWatchRequestPageDtoConverter;
import by.bsuir.linguaserver.dto.DuoWatchRequestCatalogItemDto;
import by.bsuir.linguaserver.dto.DuoWatchRequestCatalogItemPageDto;
import by.bsuir.linguaserver.dto.PersonalDuoWatchRequestPageDto;
import by.bsuir.linguaserver.entity.*;
import by.bsuir.linguaserver.entity.Dictionary;
import by.bsuir.linguaserver.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Component
@Slf4j
public class DuoWatchRequestFacade {

    private final UserRepository userRepository;
    private final VideoContentLocRepository videoContentLocRepository;
    private final LanguageRepository languageRepository;
    private final DictionaryRepository dictionaryRepository;
    private final VideoContentLocWordRepository videoContentLocWordRepository;
    private final ViewHistoryRecordRepository viewHistoryRecordRepository;
    private final DuoWatchRequestRepository duoWatchRequestRepository;
    private final DuoWatchResponseRepository duoWatchResponseRepository;
    private final DuoWatchRequestToDuoWatchRequestCatalogItemDtoConverter duoWatchRequestToDuoWatchRequestCatalogItemDtoConverter;
    private final DuoWatchRequestPageToPersonalDuoWatchRequestPageDtoConverter duoWatchRequestPageToPersonalDuoWatchRequestPageDtoConverter;
    private final DuoWatchResponsePageToPersonalDuoWatchRequestPageDtoConverter duoWatchResponsePageToPersonalDuoWatchRequestPageDtoConverter;

    public DuoWatchRequestFacade(UserRepository userRepository,
                                 VideoContentLocRepository videoContentLocRepository,
                                 LanguageRepository languageRepository,
                                 DictionaryRepository dictionaryRepository,
                                 VideoContentLocWordRepository videoContentLocWordRepository,
                                 ViewHistoryRecordRepository viewHistoryRecordRepository,
                                 DuoWatchRequestRepository duoWatchRequestRepository,
                                 DuoWatchResponseRepository duoWatchResponseRepository,
                                 DuoWatchRequestToDuoWatchRequestCatalogItemDtoConverter duoWatchRequestToDuoWatchRequestCatalogItemDtoConverter,
                                 DuoWatchRequestPageToPersonalDuoWatchRequestPageDtoConverter duoWatchRequestPageToPersonalDuoWatchRequestPageDtoConverter,
                                 DuoWatchResponsePageToPersonalDuoWatchRequestPageDtoConverter duoWatchResponsePageToPersonalDuoWatchRequestPageDtoConverter) {
        this.userRepository = userRepository;
        this.videoContentLocRepository = videoContentLocRepository;
        this.languageRepository = languageRepository;
        this.dictionaryRepository = dictionaryRepository;
        this.videoContentLocWordRepository = videoContentLocWordRepository;
        this.viewHistoryRecordRepository = viewHistoryRecordRepository;
        this.duoWatchResponseRepository = duoWatchResponseRepository;
        this.duoWatchRequestRepository = duoWatchRequestRepository;
        this.duoWatchRequestToDuoWatchRequestCatalogItemDtoConverter = duoWatchRequestToDuoWatchRequestCatalogItemDtoConverter;
        this.duoWatchRequestPageToPersonalDuoWatchRequestPageDtoConverter = duoWatchRequestPageToPersonalDuoWatchRequestPageDtoConverter;
        this.duoWatchResponsePageToPersonalDuoWatchRequestPageDtoConverter = duoWatchResponsePageToPersonalDuoWatchRequestPageDtoConverter;
    }

    public DuoWatchRequest createDuoWatchRequest(String username,
                                                 UUID videoContentLocId,
                                                 Long secondLangId) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<VideoContentLoc> videoContentLoc = videoContentLocRepository.findById(videoContentLocId);
        Optional<Language> secondLanguage = languageRepository.findById(secondLangId);
        DuoWatchRequest duoWatchRequest = new DuoWatchRequest();
        duoWatchRequest.setRequester(user.orElseThrow());
        duoWatchRequest.setVideoContentLoc(videoContentLoc.orElseThrow());
        duoWatchRequest.setSecondLanguage(secondLanguage.orElseThrow());
        LocalDate now = LocalDate.now();
        duoWatchRequest.setCreatedAt(now);
        duoWatchRequest.setUpdatedAt(now);
        duoWatchRequest.setStatus(DuoWatchRequestStatus.OPEN);
        return duoWatchRequestRepository.save(duoWatchRequest);
    }

    public DuoWatchRequestCatalogItemPageDto catalogSearchDuoWatchRequests(String username,
                                                                           String q,
                                                                           Long videoContentLangId,
                                                                           Long secondLangId,
                                                                           Integer page,
                                                                           Integer size,
                                                                           String sortType) {
        var searchResults = duoWatchRequestRepository.findBy(username, "%" + q + "%", videoContentLangId, secondLangId);
        searchResults = sortCatalogSearchResults(username, searchResults, sortType);
        var result = new DuoWatchRequestCatalogItemPageDto();
        result.setDuoWatchRequestCatalogItemDtos(buildDuoWatchRequestCatalogItemDtos(searchResults, page, size));
        result.setTotalPages(searchResults.size() / size + (searchResults.size() % size != 0 ? 1 : 0));
        return result;
    }

    private ArrayList<DuoWatchRequest> sortCatalogSearchResults(String username, ArrayList<DuoWatchRequest> duoWatchRequests, String sortType) {
        if ("views".equalsIgnoreCase(sortType)) {
            duoWatchRequests.sort((o1, o2) -> Long.compare(o2.getVideoContentLoc().getVideoContent().getPrevMonthViews(), o1.getVideoContentLoc().getVideoContent().getPrevMonthViews()));
            return duoWatchRequests;
        }
        if ("recommendation".equalsIgnoreCase(sortType)) {
            Map<Genre, Double> genreToFreqMap = prepareGenreToFreqMap(username);
            Map<Language, Set<String>> langToWordsMap = prepareLangToWordsMap(username);
            List<DuoWatchRequest> sorted = duoWatchRequests.stream()
                    .map(duoWatchRequest -> {
                        Set<String> userWords = langToWordsMap.getOrDefault(duoWatchRequest.getVideoContentLoc().getLanguage(), new HashSet<>());
                        RecommendationMetric recommendationMetric = new RecommendationMetric();
                        recommendationMetric.duoWatchRequest = duoWatchRequest;
                        recommendationMetric.metric = calcRecommendationMetric(genreToFreqMap, userWords, duoWatchRequest);
                        return recommendationMetric;
                    })
                    .sorted((o1, o2) -> Double.compare(o2.metric, o1.metric))
                    .map(recommendationMetric -> recommendationMetric.duoWatchRequest)
                    .toList();
            return new ArrayList<>(sorted);
        }
        throw new IllegalArgumentException();
    }

    private Map<Genre, Double> prepareGenreToFreqMap(String username) {
        List<ViewHistoryRecord> viewHistoryRecords = viewHistoryRecordRepository.findAllByClientUsername(username);
        Map<Genre, Integer> genreToOccurrenceMap = new HashMap<>();
        for (ViewHistoryRecord viewHistoryRecord : viewHistoryRecords) {
            Collection<Genre> genres = viewHistoryRecord.getVideoContentLoc().getVideoContent().getGenres();
            for (Genre genre : genres) {
                genreToOccurrenceMap.put(genre, genreToOccurrenceMap.getOrDefault(genre, 0) + 1);
            }
        }
        Map<Genre, Double> genreToFreqMap = new HashMap<>();
        double size = viewHistoryRecords.size();
        genreToOccurrenceMap.forEach((genre, occurrence) -> genreToFreqMap.put(genre, occurrence / size));
        return genreToFreqMap;
    }

    private Map<Language, Set<String>> prepareLangToWordsMap(String username) {
        List<Dictionary> dictionaries = dictionaryRepository.findAllByOwnerUsername(username);
        Map<Language, Set<String>> langToWords = new HashMap<>();
        for (Dictionary dictionary : dictionaries) {
            Language lang = dictionary.getFirstLanguage();
            List<String> words = dictionary.getDictionaryWords().stream().map(DictionaryWord::getFirstLanguageText).toList();
            if (!langToWords.containsKey(lang)) {
                langToWords.put(lang, new HashSet<>());
            }
            langToWords.get(lang).addAll(words);
        }
        return langToWords;
    }

    private double calcRecommendationMetric(Map<Genre, Double> genreToFreqMap, Set<String> userWords, DuoWatchRequest duoWatchRequest) {
        double genreRecommendationMetric = calcGenreRecommendationMetric(genreToFreqMap, duoWatchRequest);
        double wordRecommendationMetric = calcWordRecommendationMetric(userWords, duoWatchRequest);
        return 0.4 * genreRecommendationMetric + 0.6 * wordRecommendationMetric;
    }

    private double calcGenreRecommendationMetric(Map<Genre, Double> genreToFreqMap, DuoWatchRequest duoWatchRequest) {
        Collection<Genre> genres = duoWatchRequest.getVideoContentLoc().getVideoContent().getGenres();
        int size = genres.size();
        double total = genres.stream()
                .mapToDouble(genre -> genreToFreqMap.getOrDefault(genre, 0.0))
                .sum();
        return total / size;
    }

    private double calcWordRecommendationMetric(Set<String> userWords, DuoWatchRequest duoWatchRequest) {
        VideoContentLoc videoContentLoc = duoWatchRequest.getVideoContentLoc();
        ArrayList<VideoContentLocWord> videoContentLocWords = videoContentLocWordRepository.findAllByVideoContentLoc(videoContentLoc);
        double intersection = videoContentLocWords.stream()
                .filter(videoContentLocWord -> userWords.contains(videoContentLocWord.getWord()))
                .count();
        return intersection / videoContentLocWords.size();
    }

    private Collection<DuoWatchRequestCatalogItemDto> buildDuoWatchRequestCatalogItemDtos(ArrayList<DuoWatchRequest> searchResults,
                                                                                          Integer page,
                                                                                          Integer size) {
        int start = page * size;
        int end = Math.min(start + size, searchResults.size());
        Collection<DuoWatchRequestCatalogItemDto> result = new ArrayList<>();
        for (int i = start; i < end; i++) {
            result.add(duoWatchRequestToDuoWatchRequestCatalogItemDtoConverter.convert(searchResults.get(i)));
        }
        return result;
    }

    public PersonalDuoWatchRequestPageDto personalSearchDuoWatchRequests(String username,
                                                                         String q,
                                                                         Boolean owner,
                                                                         DuoWatchRequestStatus status,
                                                                         Integer page,
                                                                         Integer size) {
        if (owner) {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "updatedAt");
            return duoWatchRequestPageToPersonalDuoWatchRequestPageDtoConverter.convert(duoWatchRequestRepository.findBy(username, "%" + q + "%", status, pageRequest));
        } else {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "duoWatchRequest.updatedAt");
            return duoWatchResponsePageToPersonalDuoWatchRequestPageDtoConverter.convert(duoWatchResponseRepository.findBy(username, "%" + q + "%", status, pageRequest));
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void acceptDuoWatchRequest(String username,
                                      Long duoWatchRequestId) {
        DuoWatchRequest duoWatchRequest = duoWatchRequestRepository.findById(duoWatchRequestId).orElseThrow();
        if (duoWatchRequest.getRequester().getUsername().equals(username)) {
            acceptDuoWatchRequestAsRequester(duoWatchRequest);
        } else {
            acceptDuoWatchRequestAsResponder(username, duoWatchRequest);
        }
    }

    private void acceptDuoWatchRequestAsRequester(DuoWatchRequest duoWatchRequest) {
        duoWatchRequest.setStatus(DuoWatchRequestStatus.CLOSED);
        duoWatchRequestRepository.save(duoWatchRequest);
    }

    private void acceptDuoWatchRequestAsResponder(String username,
                                                  DuoWatchRequest duoWatchRequest) {
        User user = userRepository.findByUsername(username).orElseThrow();

        DuoWatchResponse duoWatchResponse = new DuoWatchResponse();
        duoWatchResponse.setResponder(user);
        duoWatchResponse.setDuoWatchRequest(duoWatchRequest);
        duoWatchResponse.setActive(true);
        duoWatchResponseRepository.save(duoWatchResponse);

        duoWatchRequest.setStatus(DuoWatchRequestStatus.ON_HOLD);
        duoWatchRequest.getDuoWatchResponses().add(duoWatchResponse);
        duoWatchRequestRepository.save(duoWatchRequest);
    }

    private static class RecommendationMetric {
        DuoWatchRequest duoWatchRequest;
        double metric;
    }
}
