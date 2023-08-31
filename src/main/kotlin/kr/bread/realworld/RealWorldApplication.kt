package kr.bread.realworld

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.web.config.EnableSpringDataWebSupport

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties
class RealWorldApplication

fun main(args: Array<String>) {
    runApplication<RealWorldApplication>(*args)
}
