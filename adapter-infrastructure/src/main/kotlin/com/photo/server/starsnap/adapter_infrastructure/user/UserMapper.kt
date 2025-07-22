package com.photo.server.starsnap.adapter_infrastructure.user

import com.photo.server.starsnap.adapter_infrastructure.user.entity.BlackUserEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.FollowEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.Oauth2Entity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.user.entity.BlackUser
import com.photo.server.starsnap.domain.user.entity.Follow
import com.photo.server.starsnap.domain.user.entity.Oauth2
import com.photo.server.starsnap.domain.user.entity.User

object UserMapper {
    fun UserEntity.toUser(
    ) = User(
        id = this.id,
        username = this.username,
        email = this.email,
        createdAt = this.createdAt,
        modifiedAt = this.modifiedAt,
        authority = this.authority,
        password = this.password
    )

    fun Oauth2Entity.toOauth2() = Oauth2(
        id = this.id,
        type = this.type,
        email = this.email,
        sub = this.sub,
        user = this.user.toUser(),
        createdAt = this.createdAt,
        issuerUris = this.issuerUris
    )

    fun FollowEntity.toFollow() = Follow(
        id = this.id,
        followerUser = this.followerUser.toUser(),
        followingUser = this.followingUser.toUser(),
        createdAt = this.createdAt
    )


    fun BlackUserEntity.toBlackUser() = BlackUser(
        id = this.id,
        createdAt = this.createdAt,
        userEntity = this.userEntity.toUser(),
        blackUserEntity = this.blackUserEntity.toUser()
    )
}