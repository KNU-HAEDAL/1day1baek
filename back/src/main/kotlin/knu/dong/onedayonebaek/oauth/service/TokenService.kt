package knu.dong.onedayonebaek.oauth.service

import com.nimbusds.jose.util.StandardCharset
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import knu.dong.onedayonebaek.oauth.dto.Token
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey


@Service
class TokenService(
    @Value("\${custom.security.secret-key}") private var secretKey: String,
    private val environment: Environment
) {

    private lateinit var key: SecretKey
    private lateinit var profile: String

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
        key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharset.UTF_8))

        profile = environment.activeProfiles.getOrElse(0) { "prod" }
    }


    fun generateToken(id: String, role: String): Token {
        val tokenPeriod =
            if (profile == "develop") { 1000L * 60L * 60L }
            else { 1000L * 60L * 10L }
        val refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L

        val claims = Jwts.claims().setSubject(id)
        claims["role"] = role
        val now = Date()

        return Token(
            Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(Date(now.time + tokenPeriod))
                .signWith(key)
                .compact(),
            Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(Date(now.time + refreshPeriod))
                .signWith(key)
                .compact()
        )
    }


    fun verifyToken(token: String): Boolean {
        return try {
            val parser = Jwts.parserBuilder().setSigningKey(key).build()
            val claims = parser.parseClaimsJws(token)

            claims.body.expiration.after(Date())
        } catch (e: Exception) {
            false
        }
    }

    fun getUid(token: String): String =
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body.subject
}