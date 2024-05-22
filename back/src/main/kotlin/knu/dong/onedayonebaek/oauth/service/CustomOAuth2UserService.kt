package knu.dong.onedayonebaek.oauth.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.*


@Service
class CustomOAuth2UserService: OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2UserService: OAuth2UserService<OAuth2UserRequest, OAuth2User> = DefaultOAuth2UserService()
        val oAuth2User = oAuth2UserService.loadUser(userRequest)

        val attributes = oAuth2User.attributes

        val memberAttribute: MutableMap<String, Any> = HashMap()
        memberAttribute["id"] = attributes["login"]!!
        memberAttribute["key"] = attributes["login"]!!
        memberAttribute["name"] = attributes["name"]!!
        memberAttribute["email"] = attributes["email"]!!
        memberAttribute["profileUrl"] = attributes["avatar_url"]!!

        return DefaultOAuth2User(
            Collections.singleton(SimpleGrantedAuthority("ROLE_USER")),
            memberAttribute, "id"
        )
    }
}