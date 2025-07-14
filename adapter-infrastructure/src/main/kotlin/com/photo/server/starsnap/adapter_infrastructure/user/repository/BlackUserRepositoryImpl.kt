package com.photo.server.starsnap.adapter_infrastructure.user.repository

import com.photo.server.starsnap.adapter_infrastructure.extension.toCommonSlice
import com.photo.server.starsnap.adapter_infrastructure.extension.toSpringPageable
import com.photo.server.starsnap.adapter_infrastructure.user.UserMapper.toBlackUser
import com.photo.server.starsnap.adapter_infrastructure.user.UserMapper.toUser
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.adapter_infrastructure.user.repository.springdata.BlackUserCrudRepository
import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.common.map
import com.photo.server.starsnap.domain.user.entity.BlackUser
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.domain.user.repository.BlackUserRepository
import org.springframework.stereotype.Repository

@Repository
class BlackUserRepositoryImpl(
    private val blackUserCrudRepository: BlackUserCrudRepository
): BlackUserRepository {
    override fun deleteByUserAndBlackUser(user: User, blackUser: User) {
        return blackUserCrudRepository.deleteByUserAndBlackUser(UserEntity.fromDomain(user), UserEntity.fromDomain(blackUser))
    }

    override fun findSliceBy(pageable: PageRequest, user: User): Slice<BlackUser>? {
        return blackUserCrudRepository.findSliceBy(pageable.toSpringPageable(), UserEntity.fromDomain(user))?.toCommonSlice()?.map { it.toBlackUser() }
    }

    override fun findUserBy(user: User): List<User> {
        return blackUserCrudRepository.findUserBy(UserEntity.fromDomain(user)).map { it.toUser() }
    }

    override fun save(blackUser: BlackUser): BlackUser {
        val blackUserEntity = com.photo.server.starsnap.adapter_infrastructure.user.entity.BlackUserEntity.fromDomain(blackUser)
        return blackUserCrudRepository.save(blackUserEntity).toBlackUser()
    }
}