package com.photo.server.starsnap.domain.photo.controller

import com.photo.server.starsnap.domain.photo.service.PhotoService
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/photo")
class PhotoController(
    private val photoService: PhotoService
) {

    @PostMapping("/create")
    fun createPhoto(@AuthenticationPrincipal user: CustomUserDetails) {
        return photoService.createPhoto()
    }

    @PostMapping("/send")
    fun sendPhoto(@RequestParam page: Int, @RequestParam size: Int) {
        return photoService.sendPhoto(page, size)
    }

    @PatchMapping("/fix")
    fun fixPhoto(@RequestParam id: String, @AuthenticationPrincipal user: CustomUserDetails) {
        return photoService.fixPhoto(id, user.username)
    }

    @DeleteMapping("/delete")
    fun deletePhoto(@RequestParam id: String, @AuthenticationPrincipal user: CustomUserDetails) {
        return photoService.deletePhoto(id, user.username)
    }

}