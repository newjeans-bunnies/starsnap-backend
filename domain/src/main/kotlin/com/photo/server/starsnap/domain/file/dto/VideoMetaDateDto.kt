package com.photo.server.starsnap.domain.file.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class VideoMetaDateDto @JsonCreator constructor(
    @JsonProperty("fileKey") val fileKey: String,
    @JsonProperty("aiState") val aiState: Boolean,
    @JsonProperty("dateTaken") val dateTaken: LocalDateTime?,
    @JsonProperty("source") val source: String,
    @JsonProperty("userId") val userId: String,
    @JsonProperty("fileSize") val fileSize: Long,
    @JsonProperty("contentType") val contentType: String
)
