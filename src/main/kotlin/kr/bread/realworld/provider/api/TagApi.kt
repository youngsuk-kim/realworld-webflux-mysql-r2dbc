package kr.bread.realworld.provider.api

import kr.bread.realworld.domain.tag.TagService
import kr.bread.realworld.provider.ApiEndpoints
import kr.bread.realworld.provider.TagNestedHttpWrapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TagApi(
    private val tagService: TagService
) {
    @GetMapping(ApiEndpoints.GET_TAGS_ENDPOINT)
    suspend fun getTags() = TagNestedHttpWrapper(tagService.findTags())
}
