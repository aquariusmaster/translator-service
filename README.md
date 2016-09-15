# translator
Translator service. 

This service wait for entries from docker containers which fill with redisfiller and elasticsearchfiller, and put data together in localy installed MongoDB. Then closes docker containers through JSCH lib.


Using: 

Spring Data: Redis, Elasticsearch, MongoDB; 

Spring Batch;

JSCH;


