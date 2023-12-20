package com.ksoot.mongodb;

import com.ksoot.common.DateTimeUtils;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@EnableConfigurationProperties({MongoProperties.class, MongoAuditProperties.class})
@Configuration
@RequiredArgsConstructor
class MongoDBConfig extends AbstractMongoClientConfiguration {

    private final MongoProperties mongoProperties;

    private final MongoAuditProperties mongoAuditProperties;

    @Override
    protected void configureClientSettings(final MongoClientSettings.Builder builder) {
        builder
                .applyConnectionString(new ConnectionString(this.mongoProperties.determineUri()))
                .uuidRepresentation(this.mongoProperties.getUuidRepresentation());
    }

    @Override
    protected boolean autoIndexCreation() {
        return this.mongoProperties.isAutoIndexCreation();
    }

    @Override
    protected String getDatabaseName() {
        return this.mongoProperties.getDatabase();
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return this.mongoAuditProperties.getEntityBasePackages();
    }

    @Bean
    ValidatingMongoEventListener validatingMongoEventListener(
            final LocalValidatorFactoryBean factory) {
        return new ValidatingMongoEventListener(factory);
    }

    @Bean
    LocalValidatorFactoryBean validatorFactory() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        return validator;
    }

    @Bean
    MongoTransactionManager transactionManager(final MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }

    @Override
    public MongoCustomConversions customConversions() {
        List<Object> converters = new ArrayList<>();
        converters.add(new ZonedDateTimeReadConverter());
        converters.add(new ZonedDateTimeWriteConverter());
        converters.add(new OffsetDateTimeReadConverter());
        converters.add(new OffsetDateTimeWriteConverter());
        return new MongoCustomConversions(converters);
    }

    @ReadingConverter
    class ZonedDateTimeReadConverter implements Converter<Date, ZonedDateTime> {

        @Override
        public ZonedDateTime convert(final Date source) {
            return source.toInstant().atZone(DateTimeUtils.SYSTEM_ZONE_ID);
        }
    }

    @WritingConverter
    class ZonedDateTimeWriteConverter implements Converter<ZonedDateTime, Date> {

        @Override
        public Date convert(final ZonedDateTime source) {
            return Date.from(source.toInstant());
        }
    }

    @ReadingConverter
    class OffsetDateTimeReadConverter implements Converter<Date, OffsetDateTime> {

        @Override
        public OffsetDateTime convert(final Date source) {
            return source.toInstant().atZone(DateTimeUtils.SYSTEM_ZONE_ID).toOffsetDateTime();
        }
    }

    @WritingConverter
    class OffsetDateTimeWriteConverter implements Converter<OffsetDateTime, Date> {

        @Override
        public Date convert(final OffsetDateTime source) {
            return Date.from(source.toInstant());
        }
    }
}
