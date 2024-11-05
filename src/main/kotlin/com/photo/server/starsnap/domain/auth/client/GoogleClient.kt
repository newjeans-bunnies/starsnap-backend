package com.photo.server.starsnap.domain.auth.client

import com.photo.server.starsnap.domain.auth.dto.OIDCOpenKeys
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "google", url = "https://www.googleapis.com/")
@Qualifier("google")
interface GoogleClient {
    @GetMapping("oauth2/v3/certs")
    @Cacheable(cacheNames = ["googleOIDC"], cacheManager = "googleOIDCCacheManager")
    fun getGoogleOIDCOpenKeys(): OIDCOpenKeys
}