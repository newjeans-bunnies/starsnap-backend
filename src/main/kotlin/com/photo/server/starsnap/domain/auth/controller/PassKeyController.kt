package com.photo.server.starsnap.domain.auth.controller

import com.photo.server.starsnap.domain.auth.service.PassKeysService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class PassKeyController(
    private val passKeysService: PassKeysService
) {
    @GetMapping(".well-known/assetlinks.json")
    fun passkey(): ResponseEntity<String> {
        val json = """[
    {
        "relation" : [
            "delegate_permission/common.handle_all_urls",
            "delegate_permission/common.get_login_creds"
        ],
        "target" : {
            "namespace" : "starsnap",
            "package_name" : "com.photo.starsnap",
            "sha256_cert_fingerprints" : [
                "66:13:7C:74:64:17:19:19:30:48:E3:49:2D:DC:58:90:50:B0:9A:62:29:56:2A:B5:B5:75:3D:C8:8B:6D:75:B1"
            ]
        }
    }
]
        """.trimIndent()

        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json")

        return ResponseEntity(json, headers, HttpStatus.OK)
    }


    @PostMapping("/api/passkey/create")
    fun createPassKey() {
        passKeysService.createPassKeys()
    }
}