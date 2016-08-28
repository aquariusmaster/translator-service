package com.karienomen.translator.batch.redis;

import com.karienomen.translator.batch.elasticsearch.UserItemProcessor;
import com.karienomen.translator.domain.BitcoinRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by karienomen on 26.08.16.
 */
public class BitcoinRecordItemProcessor implements ItemProcessor<BitcoinRecord, BitcoinRecord> {

    private static Logger logger = LoggerFactory.getLogger(BitcoinRecordItemProcessor.class);

    @Override
    public BitcoinRecord process(BitcoinRecord bitcoinRecord) throws Exception {
        logger.debug("Redis Proccess return: " + bitcoinRecord);
        return bitcoinRecord;
    }
}
