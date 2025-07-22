package com.photo.server.starsnap.domain.file.type

enum class Status {
    INIT, // 사진 업로드 전
    PENDING, // 사진 업로드 후 게시글 연결 전
    LINKED, // 게시글 연결 완료
    EXPIRED // 게시글 연결이 안됨
}