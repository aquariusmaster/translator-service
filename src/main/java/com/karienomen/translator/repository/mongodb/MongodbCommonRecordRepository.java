package com.karienomen.translator.repository.mongodb;

import com.karienomen.translator.domain.BitcoinRecord;
import com.karienomen.translator.domain.CommonRecord;
import com.karienomen.translator.domain.User;
import com.karienomen.translator.repository.redis.BitcoinRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by karienomen on 28.08.16.
 */
@Repository
public class MongodbCommonRecordRepository {

    private static Logger logger = LoggerFactory.getLogger(BitcoinRecordRepository.class);

    @Autowired
    CommonRecordRepository recordRepository;

    HashMap<Long,CommonRecord> commonRecords = new HashMap<>();

    public void saveBitcoinRecord(List<? extends BitcoinRecord> bitcoinRecords){
        for (BitcoinRecord record : bitcoinRecords){
            CommonRecord commonRecord = commonRecords.get(record.getId());
            if (commonRecord == null){
                commonRecord = new CommonRecord();
                commonRecord.setId(record.getId());
                commonRecord.setBitcoin(record.getBitcoin());
                commonRecords.put(commonRecord.getId(), commonRecord);
                logger.info("CommonRecord bitcoin part saved: " + commonRecord);
            }else{
                commonRecord.setBitcoin(record.getBitcoin());
                recordRepository.save(commonRecords.get(record.getId()));
                commonRecords.remove(record.getId());
                logger.info("CommonRecord full saved: " + recordRepository.findOne(commonRecord.getId()));
            }
        }
    }

    public void saveUser(List<? extends User> users){
        for (User user : users){
            CommonRecord commonRecord = commonRecords.get(user.getId());
            if (commonRecord == null){
                commonRecord = new CommonRecord();
                commonRecord.setId(user.getId());
                commonRecord.setFirst_name(user.getFirst_name());
                commonRecord.setLast_name(user.getLast_name());
                commonRecord.setEmail(user.getEmail());
                commonRecord.setGender(user.getGender());
                commonRecord.setIp_address(user.getIp_address());
                commonRecords.put(commonRecord.getId(), commonRecord);
                logger.info("CommonRecord user part saved: " + commonRecord);
            }else{
                commonRecord.setFirst_name(user.getFirst_name());
                commonRecord.setLast_name(user.getLast_name());
                commonRecord.setEmail(user.getEmail());
                commonRecord.setGender(user.getGender());
                commonRecord.setIp_address(user.getIp_address());
                recordRepository.save(commonRecords.get(user.getId()));
                commonRecords.remove(user.getId());
                logger.info("CommonRecord full saved: " + recordRepository.findOne(commonRecord.getId()));
            }
        }
    }
}
