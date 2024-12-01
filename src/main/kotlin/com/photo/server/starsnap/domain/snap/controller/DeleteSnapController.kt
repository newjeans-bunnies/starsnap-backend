package com.photo.server.starsnap.domain.snap.controller

import com.photo.server.starsnap.domain.snap.service.DeleteSnapService
import com.photo.server.starsnap.global.annotation.AuthenticationPrincipalId
import com.photo.server.starsnap.global.dto.SnapDto
import org.springframework.data.domain.Slice
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/snap/")
class DeleteSnapController(
    private val deleteSnapService: DeleteSnapService
) {
    @GetMapping("/delete/get")
    fun getDeleteSnap(
        @RequestParam size: Int,
        @RequestParam page: Int,
        @AuthenticationPrincipalId userId: String
    ): Slice<SnapDto> {
        return deleteSnapService.getDeleteSnap(page, size, userId)
    }

    @PatchMapping("/delete/rollback")
    fun rollbackSnap(
        @AuthenticationPrincipalId userId: String,
        @RequestParam("snap-id") snapId: String
    ): SnapDto {
        return deleteSnapService.rollbackSnap(snapId, userId)
    }
}