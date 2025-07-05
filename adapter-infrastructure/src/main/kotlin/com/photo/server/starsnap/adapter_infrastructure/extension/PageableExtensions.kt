package com.photo.server.starsnap.adapter_infrastructure.extension

import com.photo.server.starsnap.domain.common.Direction
import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.SortSpec
import org.springframework.data.domain.Pageable as SpringPageable
import org.springframework.data.domain.Sort
import org.springframework.data.domain.PageRequest as SpringPageRequest

/**
 * Spring Data 의 Pageable → 도메인 PageRequest 변환
 */
fun SpringPageable.toDomainPageRequest(): PageRequest =
    PageRequest(
        page = this.pageNumber,
        size = this.pageSize,
        sort = this.sort
            .map { order ->
                SortSpec(
                    property = order.property,
                    direction = when (order.direction) {
                        Sort.Direction.ASC -> Direction.ASC
                        Sort.Direction.DESC -> Direction.DESC
                    }
                )
            }.toList(),
    )

/**
 * 도메인 PageRequest → Spring Data Pageable 변환
 */
fun PageRequest.toSpringPageable(): SpringPageable {
    val springSort = if (sort.isEmpty()) {
        Sort.unsorted()
    } else {
        Sort.by(
            sort.map { spec ->
                // Spring Data 의 Direction 으로 변환
                val direction = Sort.Direction.valueOf(spec.direction.name)
                // Order 객체 생성
                Sort.Order(direction, spec.property)
            }
        )
    }
    return SpringPageRequest.of(page, size, springSort)
}

