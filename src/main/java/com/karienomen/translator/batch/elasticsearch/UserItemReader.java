package com.karienomen.translator.batch.elasticsearch;

import com.karienomen.translator.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * Created by karienomen on 23.08.16.
 */
public class UserItemReader implements ItemReader<User> {

    private static Logger logger = LoggerFactory.getLogger(UserItemReader.class);

    @Autowired
    private ElasticsearchCrudRepository elasticRepo;

    private static long count = 1L;

    @Override
    public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        User user = (User) elasticRepo.findOne(count++);
        logger.debug("Elasticsearch reader return: " + user);
        
        return user;
    }
}