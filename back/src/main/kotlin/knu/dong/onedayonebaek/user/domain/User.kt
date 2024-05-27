package knu.dong.onedayonebaek.user.domain

import jakarta.persistence.*
import knu.dong.onedayonebaek.containgroup.domain.ContainGroup


@Entity
@Table(name = "users")
class User(
    @Column(nullable = false)
    var loginId: String,

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = false)
    val profileUrl: String = "",

    @OneToMany(mappedBy = "user")
    val groups: MutableSet<ContainGroup> = mutableSetOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}