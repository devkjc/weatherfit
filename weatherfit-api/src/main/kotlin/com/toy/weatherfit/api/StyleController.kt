package com.toy.weatherfit.api

import com.toy.weatherfit.style.domain.Style
import com.toy.weatherfit.style.service.StyleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/style")
class StyleController(
    private val styleService: StyleService,
) {

    /**
     * 스타일 조회
     * @return List<Style>
     */
    @GetMapping
    fun getStyles(): List<Style> {
        return styleService.getStyles()
    }

}