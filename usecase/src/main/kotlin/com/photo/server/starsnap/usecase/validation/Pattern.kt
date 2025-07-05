package com.photo.server.starsnap.usecase.validation

object Pattern {
    const val USERNAME = "^[a-zA-Z0-9]{4,12}\$"
    const val PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\d)(?=.*[~․!@#\$%^&*()_\\-+=|\\\\;:‘“<>,.?/]).{8,50}\$"
    const val EMAIL = "^[a-zA-Z0-9+-_.]+@[0-9a-zA-Z]+\\.[a-zA-Z]{2,3}\$"
}