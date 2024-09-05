package com.photo.server.starsnap.global.config

import org.neo4j.cypherdsl.core.renderer.Dialect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.neo4j.cypherdsl.core.renderer.Configuration as CypherConfiguration


@Configuration
class Neo4jConfig {
    @Bean
    fun cypherDslConfiguration(): CypherConfiguration {
        return CypherConfiguration.newConfig()
            .withDialect(Dialect.NEO4J_5)
            .build()
    }
}