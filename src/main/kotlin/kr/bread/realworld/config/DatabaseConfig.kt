package kr.bread.realworld.config

import io.github.oshai.kotlinlogging.KotlinLogging
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@Configuration
class DatabaseConfig {

    private val log = KotlinLogging.logger {}

    @Bean
    fun init(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        return ConnectionFactoryInitializer().apply {
            setConnectionFactory(connectionFactory)
            setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource(INITIAL_SCHEMA_DIR)))
        }.also {
            log.info { "Schema init completed in <<directory : $INITIAL_SCHEMA_DIR>>" }
        }
    }

    companion object {
        private const val INITIAL_SCHEMA_DIR = "/scripts/schema.sql"
    }
}
