package com.photo.server.starsnap.domain.auth.client

import com.photo.server.starsnap.domain.auth.dto.OIDCOpenKeys
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "appleClient", url = "https://appleid.apple.com/")
@Qualifier("apple")
interface AppleClient {
    @GetMapping("auth/keys")
    @Cacheable(cacheNames = ["appleOIDC"], cacheManager = "appleOIDCCacheManager")
    fun getAppleOIDCOpenKeys(): OIDCOpenKeys
}