package com.karienomen.translator.repository.redis;

import com.karienomen.translator.domain.BitcoinRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

/**
 * Created by karienomen on 23.08.16.
 */
@Repository
public class BitcoinRecordRepository {

    private static Logger logger = LoggerFactory.getLogger(BitcoinRecordRepository.class);
    public static final String KEY = "bitcoins:";
    private static long count = 1L;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public BitcoinRecord findBitcoinRecord(long id){

        if (logger.isDebugEnabled()){
            logger.debug("Trying to get record with id=" + id );
        }
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        String bitcoin = values.get(KEY + id);
        if (bitcoin == null) {
            return null;
        }
        BitcoinRecord retrived = new BitcoinRecord(id, bitcoin);
        if (logger.isDebugEnabled()){
            logger.debug("Retrived from record with id = " + id + ": " + retrived );
        }
        return retrived;
    }

}
