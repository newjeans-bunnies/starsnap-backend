package com.photo.server.starsnap.domain.auth.type

enum class Oauth2IssuerUris(val issuerUris: Set<String>) {
    APPLE(setOf("https://appleid.apple.com")),
    GOOGLE(setOf("https://accounts.google.com", "https://accounts.google.com/"));
}