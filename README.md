# translator
Deep Server-Side Translator. 

This service wait for entries from docker containers which fill with **redisfiller** and **elasticsearchfiller**, and put data together in localy installed MongoDB. Then closes docker containers through JSCH lib.


Using: 

Spring Data: Redis, Elasticsearch, MongoDB; 

Spring Batch;

JSCH;

To run Deep Server-Side Translator specify the path to the **redisfiller.jar** and **elasticsearchfiller.jar** in **translator.sh** file.

