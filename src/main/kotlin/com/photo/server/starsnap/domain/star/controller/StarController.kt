package com.photo.server.starsnap.domain.star.controller

import com.photo.server.starsnap.domain.star.service.StarService
import org.springframework.web.bind.annotation.RestController

@RestController
class StarController(
    private val starService: StarService
) {
}