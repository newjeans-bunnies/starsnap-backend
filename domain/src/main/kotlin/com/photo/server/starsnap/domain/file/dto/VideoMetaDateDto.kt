package com.photo.server.starsnap.domain.file.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class VideoMetaDateDto @JsonCreator constructor(
    @JsonProperty("fileKey") val fileKey: String,
    @JsonProperty("fileSize") val fileSize: String,
    @JsonProperty("contentType") val contentType: String
)
