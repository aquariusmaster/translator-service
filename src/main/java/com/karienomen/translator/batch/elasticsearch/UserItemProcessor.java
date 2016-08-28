package com.karienomen.translator.batch.elasticsearch;

import com.karienomen.translator.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by karienomen on 23.08.16.
 */
public class UserItemProcessor implements ItemProcessor<User, User> {

    private static Logger logger = LoggerFactory.getLogger(UserItemProcessor.class);

    @Override
    public User process(User user) throws Exception {
        logger.debug("Elasticsearch proccessor return: " + user);
        return user;
    }
}
