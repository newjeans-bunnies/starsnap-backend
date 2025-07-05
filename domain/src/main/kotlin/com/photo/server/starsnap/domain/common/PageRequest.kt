package com.photo.server.starsnap.domain.common

/**
 * 도메인 계층 전용 페이지 요청 정보
 */
data class PageRequest(
    val page: Int = 0,
    val size: Int = 20,
    val sort: List<SortSpec> = emptyList()
)

data class SortSpec(
    val property: String,
    val direction: Direction = Direction.ASC
)

enum class Direction { ASC, DESC }