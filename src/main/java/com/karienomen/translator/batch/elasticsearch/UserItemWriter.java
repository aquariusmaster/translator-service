package com.karienomen.translator.batch.elasticsearch;

import com.karienomen.translator.domain.User;
import com.karienomen.translator.repository.mongodb.MongodbCommonRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karienomen on 23.08.16.
 */
public class UserItemWriter implements ItemWriter<User> {

    private static Logger logger = LoggerFactory.getLogger(UserItemWriter.class);

    private List<User> userList = new ArrayList<User>();

    @Autowired
    MongodbCommonRecordRepository mongodbRepository;

    @Override
    public void write(List<? extends User> list) throws Exception {

        if (list.get(0).getId() == 0L){
            return;
        }
        userList.addAll(list);
        logger.info("Elasticsearch writer get list: " + list);
        logger.info("Elasticsearch writer save: " + userList);
        mongodbRepository.saveUser(list);
    }
}
