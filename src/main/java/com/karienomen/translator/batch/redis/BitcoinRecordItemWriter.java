package com.karienomen.translator.batch.redis;

import com.karienomen.translator.batch.elasticsearch.UserItemWriter;
import com.karienomen.translator.domain.BitcoinRecord;
import com.karienomen.translator.domain.CommonRecord;
import com.karienomen.translator.domain.User;
import com.karienomen.translator.repository.mongodb.CommonRecordRepository;
import com.karienomen.translator.repository.mongodb.MongodbCommonRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karienomen on 26.08.16.
 */
public class BitcoinRecordItemWriter implements ItemWriter<BitcoinRecord> {

    private static Logger logger = LoggerFactory.getLogger(BitcoinRecordItemWriter.class);

    private List<BitcoinRecord> bitcoinRecords = new ArrayList<>();

    @Autowired
    MongodbCommonRecordRepository mongodbRepository;

    @Override
    public void write(List<? extends BitcoinRecord> list) throws Exception {
        bitcoinRecords.addAll(list);
        logger.debug("RedisWriter return: " + bitcoinRecords);

        mongodbRepository.saveBitcoinRecord(list);
    }
}
