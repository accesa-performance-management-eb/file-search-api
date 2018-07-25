package com.biroas.poc.file.search.api.service;

import com.biroas.poc.file.search.api.constants.FileFields;
import com.biroas.poc.file.search.api.model.file.File;
import com.biroas.poc.file.search.api.model.query.QueryFilter;
import com.biroas.poc.file.search.api.model.query.RangeDate;
import com.biroas.poc.file.search.api.model.query.RangeNumeric;
import com.biroas.poc.file.search.api.model.result.SearchResult;
import com.biroas.poc.file.search.api.repository.FileRepository;
import org.apache.lucene.queryparser.flexible.standard.QueryParserUtil;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.inject.Inject;

@Service
public class FileSearchService {

    private final Logger logger = LoggerFactory.getLogger(FileSearchService.class);

    private FileRepository fileRepository;
    private ElasticsearchTemplate elasticsearchTemplate;

    @Inject
    public FileSearchService(FileRepository fileRepository, ElasticsearchTemplate elasticsearchTemplate) {
        this.fileRepository = fileRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    public File save(File file) {
        return fileRepository.save(file);
    }

    public SearchResult findAll(Pageable paging) {
        Page<File> filePage = fileRepository.findAll(paging);

        return getSearchResult(filePage);
    }

    public SearchResult findByFileContent(String fileContent, Pageable paging) {
        Page<File> filePage = fileRepository.findByFileNameContainingOrFileContentContaining(fileContent, fileContent, paging);
        return getSearchResult(filePage);
    }

    public SearchResult find(QueryFilter filter, PageRequest pageRequest) {
        logger.info("Received new search request: {}", filter);

        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();

        addStringFilter(query, FileFields.FILE_NAME, filter.getFileName());
        addStringFilter(query, FileFields.PARENT_DIRECTORY, filter.getParentDirectory());
        addStringFilter(query, FileFields.SYSTEM_NAME, filter.getSystemName());
        addStringFilter(query, FileFields.FILE_CONTENT, filter.getContent());
        addRangeNumericFilter(query, FileFields.SIZE, filter.getSizeRange());
        addRangeDateFilter(query, FileFields.CREATION_DATE, filter.getCreatedRange());
        addRangeDateFilter(query, FileFields.MODIFIED_DATE, filter.getModifiedRange());
        addRangeDateFilter(query, FileFields.ACCESSED_DATE, filter.getAccessedRange());

        Page<File> files = elasticsearchTemplate.queryForPage(query.withPageable(pageRequest).build(), File.class);

        return getSearchResult(files);
    }

    private void addStringFilter(NativeSearchQueryBuilder query, String fieldName, String fieldValue) {
        if (!ObjectUtils.isEmpty(fieldValue)) {
            query.withQuery(QueryBuilders.queryStringQuery(QueryParserUtil.escape(fieldValue)).field(fieldName));
        }
    }

    private void addRangeDateFilter(NativeSearchQueryBuilder query,String fieldName, RangeDate range) {
        if (!ObjectUtils.isEmpty(range)) {

            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(fieldName);

            if (!ObjectUtils.isEmpty(range.getFrom())) {
                rangeQuery.from(range.getFrom().getTime());
            }

            if (!ObjectUtils.isEmpty(range.getTo())) {
                rangeQuery.from(range.getTo().getTime());
            }

            query.withQuery(rangeQuery);
        }
    }

    private void addRangeNumericFilter(NativeSearchQueryBuilder query,String fieldName, RangeNumeric range) {
        if (!ObjectUtils.isEmpty(range)) {

            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(fieldName);

            if (!ObjectUtils.isEmpty(range.getFrom())) {
                rangeQuery.from(range.getFrom());
            }

            if (!ObjectUtils.isEmpty(range.getTo())) {
                rangeQuery.from(range.getTo());
            }

            query.withQuery(rangeQuery);
        }
    }

    private SearchResult getSearchResult(Page<File> filePage) {
        SearchResult searchResult = new SearchResult();
        searchResult.setFiles(filePage.getContent());
        searchResult.setCount(filePage.getContent().size());
        searchResult.setPageSize(filePage.getSize());
        searchResult.setPageNumber(filePage.getNumber() + 1);
        searchResult.setTotalPages(filePage.getTotalPages());
        return searchResult;
    }
}
