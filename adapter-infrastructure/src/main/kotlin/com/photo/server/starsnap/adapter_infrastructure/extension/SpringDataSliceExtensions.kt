package com.photo.server.starsnap.adapter_infrastructure.extension

import com.photo.server.starsnap.domain.common.Pageable as CommonPageable
import com.photo.server.starsnap.domain.common.SortX
import com.photo.server.starsnap.domain.common.Slice as CommonSlice
import org.springframework.data.domain.Slice as SlicePageable
import org.springframework.data.domain.Pageable as SpringPageable

/**
 * Spring Data 의 Pageable → CommonPageable 변환
 */
fun SpringPageable.toCommonPageable(): CommonPageable =
    CommonPageable(
        offset = this.offset.toInt(),
        pageNumber = this.pageNumber,
        pageSize = this.pageSize,
        paged = this.isPaged,
        sort = SortX(
            empty = this.sort.isEmpty,
            sorted = this.sort.isSorted,
            unsorted = this.sort.isUnsorted
        ),
        unpaged = this.isUnpaged
    )

/**
 * Spring Data 의 Slice<T> → Common.Slice<R> 변환
 */
fun <T> SlicePageable<T>.toCommonSlice(): CommonSlice<T> = CommonSlice(
        content = this.content,
        empty = this.isEmpty,
        first = this.isFirst,
        last = this.isLast,
        number = this.number,
        numberOfElements = this.numberOfElements,
        pageable = this.pageable.toCommonPageable(),
        size = this.size,
        sort = SortX(
            empty = this.sort.isEmpty,
            sorted = this.sort.isSorted,
            unsorted = this.sort.isUnsorted
        )
    )