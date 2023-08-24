package kr.bread.realworld.infra.r2dbc

import kr.bread.realworld.domain.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ReactiveUserRepository: ReactiveCrudRepository<User, Long>