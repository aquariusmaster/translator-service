package com.karienomen.translator.repository.mongodb;

import com.karienomen.translator.domain.CommonRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by karienomen on 28.08.16.
 */
public interface CommonRecordRepository extends MongoRepository<CommonRecord, Long> {

}
