package com.photo.server.starsnap.domain.file.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class PhotoMetaDateDto @JsonCreator constructor(
    @JsonProperty("fileKey") val fileKey: String = "",
    @JsonProperty("snapId") val snapId: String,
    @JsonProperty("fileSize") val fileSize: String,
    @JsonProperty("width") val width: String,
    @JsonProperty("height") val height: String,
    @JsonProperty("contentType") val contentType: String
)