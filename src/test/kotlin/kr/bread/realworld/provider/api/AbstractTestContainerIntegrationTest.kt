package kr.bread.realworld.provider.api

import kotlinx.coroutines.runBlocking
import kr.bread.realworld.infra.ArticleRepository
import kr.bread.realworld.infra.FollowRepository
import kr.bread.realworld.infra.TagRepository
import kr.bread.realworld.infra.UserRepository
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
abstract class AbstractIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var articleRepository: ArticleRepository

    @Autowired
    lateinit var tagRepository: TagRepository

    @Autowired
    lateinit var followRepository: FollowRepository

    @AfterEach
    fun clear() {
        runBlocking {
            articleRepository.deleteAll()
            userRepository.deleteAll()
            tagRepository.deleteAll()
            followRepository.deleteAll()
        }
    }

}
