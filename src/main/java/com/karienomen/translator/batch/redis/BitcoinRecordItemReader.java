package com.karienomen.translator.batch.redis;

import com.karienomen.translator.domain.BitcoinRecord;
import com.karienomen.translator.domain.User;
import com.karienomen.translator.repository.redis.BitcoinRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;

/**
 * Created by karienomen on 26.08.16.
 */
public class BitcoinRecordItemReader implements ItemReader<BitcoinRecord> {


    private static Logger logger = LoggerFactory.getLogger(BitcoinRecordItemReader.class);

    @Autowired
    private BitcoinRecordRepository bitcoinRecordRepository;

    private static long count = 1L;
    private static BitcoinRecord emptyBitcoinRecord = new BitcoinRecord();

    @Override
    public BitcoinRecord read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (count > 1000){
            return null;
        }
        BitcoinRecord record = null;
        try {

            record = bitcoinRecordRepository.findBitcoinRecord(count);
            if (record == null){
                record = emptyBitcoinRecord;
            }else{
                count++;
            }
        }catch(RedisConnectionFailureException e){
            logger.error("Redis connection failure. Trying wait 5 sec...");
            Thread.sleep(5000);
            throw e;
        }


        logger.debug("Redis reader return: " + record);
        return record;
    }
}
