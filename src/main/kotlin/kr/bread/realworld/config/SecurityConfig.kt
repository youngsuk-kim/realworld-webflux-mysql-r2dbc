package kr.bread.realworld.config

import kr.bread.realworld.support.annotation.Login
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono


private val allowHttpMethods = arrayOf("GET", "POST", "PUT", "DELETE")
private const val addMappingPattern = "/**"
private const val allowOrigin = "*"
private const val maxAge = 3600L

@Configuration
class SecurityConfig(
    private val authTokenResolver: AuthTokenResolver
) : WebFluxConfigurer {

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        super.configureArgumentResolvers(configurer)
        configurer.addCustomResolver(authTokenResolver)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping(addMappingPattern)
            .allowedOrigins(allowOrigin)
            .allowedMethods(*allowHttpMethods)
            .maxAge(maxAge)
    }
}

private const val SPACE = " "
private const val TOKEN_POSITION = 1

@Component
class AuthTokenResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter) = parameter.hasParameterAnnotation(Login::class.java)

    override fun resolveArgument(
        parameter: MethodParameter,
        bindingContext: BindingContext,
        exchange: ServerWebExchange
    ): Mono<Any> {
        val authHeader = exchange.request.headers[HttpHeaders.AUTHORIZATION]?.first()
        checkNotNull(authHeader)

        val token = authHeader.split(SPACE)[TOKEN_POSITION]
        return token.toMono()
    }
}
