package com.photo.server.starsnap.domain.common

data class Slice<T>(
    val content: List<T>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: SortX
)


data class Pageable(
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val sort: SortX,
    val unpaged: Boolean
)

data class SortX(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)


fun <T, R> Slice<T>.map(transform: (T) -> R): Slice<R> {
    return Slice(
        content = this.content.map(transform),
        empty = this.empty,
        first = this.first,
        last = this.last,
        number = this.number,
        numberOfElements = this.numberOfElements,
        pageable = this.pageable,
        size = this.size,
        sort = this.sort
    )
}