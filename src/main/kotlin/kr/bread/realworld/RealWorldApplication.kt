package kr.bread.realworld

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties
class RealWorldApplication

fun main(args: Array<String>) {
    runApplication<RealWorldApplication>(*args)
}
