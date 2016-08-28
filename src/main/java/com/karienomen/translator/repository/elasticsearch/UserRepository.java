package com.karienomen.translator.repository.elasticsearch;

import com.karienomen.translator.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * Created by karienomen on 15.07.16.
 */
public interface UserRepository extends ElasticsearchRepository<User, Long>, PagingAndSortingRepository<User, Long> {
}