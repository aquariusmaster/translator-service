package com.karienomen.translator.configuration;

import com.karienomen.translator.batch.elasticsearch.UserItemProcessor;
import com.karienomen.translator.batch.elasticsearch.UserItemReader;
import com.karienomen.translator.batch.elasticsearch.UserItemWriter;
import com.karienomen.translator.batch.redis.BitcoinRecordItemProcessor;
import com.karienomen.translator.batch.redis.BitcoinRecordItemReader;
import com.karienomen.translator.batch.redis.BitcoinRecordItemWriter;
import com.karienomen.translator.domain.BitcoinRecord;
import com.karienomen.translator.domain.User;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.NodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.net.InetAddress;

/**
 * Created by karienomen on 23.08.16.
 */
@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableBatchProcessing
@ComponentScan(basePackages = {"com.karienomen.translator"})
@EnableElasticsearchRepositories(basePackages = "com.karienomen.translator")
public class TranslatorConfig {

    private static Logger logger = LoggerFactory.getLogger(TranslatorConfig.class);

    @Resource
    private Environment environment;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    /* Elasticsearch config*/
    /* Batch config for elasticsearch step*/
    @Bean
    public ItemReader<User> elasticsearchreader() {
        return new UserItemReader();
    }

    @Bean
    public ItemProcessor elasticsearchprocessor() {
        return new UserItemProcessor();
    }

    @Bean
    public ItemWriter elasticsearchwriter() {
        return new UserItemWriter();
    }


    /* Bean config for elasticsearch*/

    @Bean
    public NodeBuilder nodeBuilder() {
        return new NodeBuilder();
    }

    @Bean
    public Client getNodeClient() {

        Client client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(
                            new InetSocketTransportAddress(
                                    InetAddress.getByName(environment.getProperty("elasticsearch.host")),
                                    Integer.parseInt(environment.getProperty("elasticsearch.port"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(getNodeClient());
    }

    /* Redis config*/
    /* Batch config for redis step*/
    @Bean
    public ItemReader<BitcoinRecord> redisReader() {
        return new BitcoinRecordItemReader();
    }

    @Bean
    public ItemProcessor redisProcessor() {
        return new BitcoinRecordItemProcessor();
    }

    @Bean
    public ItemWriter<BitcoinRecord> redisWriter() {
        return new BitcoinRecordItemWriter();
    }

    /* Bean config for redis*/

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName("localhost");
        connectionFactory.setPort(6379);
        connectionFactory.setUsePool(true);
        return connectionFactory;
    }

    @Bean
    public RedisTemplate<Long, String> redisTemplate() {
        final RedisTemplate<Long, String> template = new RedisTemplate<Long, String>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }


//    @Bean
//    public Job job1(JobBuilderFactory jobs, Step step1, Step step2) {
//        return jobs.get("job1")
//                .incrementer(new RunIdIncrementer())
//                .flow(step1)
////                .flow(step2)
//                .end()
//                .build();
//    }
//
//    @Bean
//    public Step step1() {
//        return stepBuilderFactory.get("step1")
//                .chunk(1)
//                .reader(elasticsearchreader())
//                .processor(elasticsearchprocessor())
//                .writer(elasticsearchwriter())
//                .build();
//    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .chunk(1)
                .reader(redisReader())
                .processor(redisProcessor())
                .writer(redisWriter())
                .build();
    }

    @Bean
    public Flow flow1() {
        return new FlowBuilder<Flow>("flow1")
                .start(stepBuilderFactory.get("step1")
                                .chunk(1)
                                .reader(elasticsearchreader())
                                .processor(elasticsearchprocessor())
                                .writer(elasticsearchwriter())
                                .build())
                .build();
    }

    @Bean
    public Flow flow2() {
        return new FlowBuilder<Flow>("flow2")
                .start(stepBuilderFactory.get("step2")
                        .chunk(1)
                        .reader(redisReader())
                        .processor(redisProcessor())
                        .writer(redisWriter())
                        .build())
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(flow1())
                .split(new SimpleAsyncTaskExecutor()).add(flow2())
                .end()
                .build();
    }


}
