package com.biroas.poc.file.search.api.repository;

import com.biroas.poc.file.search.api.model.file.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FileRepository extends ElasticsearchRepository<File, String> {

    Page<File> findByFileName(String fileName, Pageable paging);

    Page<File> findByFileNameContaining(String fileName, Pageable paging);

    Page<File> findByFileNameContainingAndParentDirectoryContaining(String fileName, String parentDir, Pageable paging);
}
