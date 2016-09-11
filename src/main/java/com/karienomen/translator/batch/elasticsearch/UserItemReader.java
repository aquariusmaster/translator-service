package com.karienomen.translator.batch.elasticsearch;

import com.karienomen.translator.domain.User;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipException;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.ElasticsearchException;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * Created by karienomen on 23.08.16.
 */
public class UserItemReader implements ItemReader<User> {

    private static Logger logger = LoggerFactory.getLogger(UserItemReader.class);
    private static User emptyUser = new User();

    @Autowired
    private ElasticsearchCrudRepository elasticRepo;

    private static long count = 1L;

    @Override
    public User read() throws Exception {

        User user = null;
        try{

            if (count > 1000) {
                return null;  //job done
            }

            user = (User) elasticRepo.findOne(count);

            if(user == null) {
                logger.info("Record is not ready. Trying wait 1 sec. ");
                user = emptyUser;
                Thread.sleep(1000);
            }else {
                count++;
            }

            logger.debug("Elasticsearch reader return: " + user);
        }catch(NoNodeAvailableException e){
            logger.error("Elasticsearch node unavailable. Sleep 5 sec...");
            Thread.sleep(5000);
            throw e;
        }

        return user;
    }

}