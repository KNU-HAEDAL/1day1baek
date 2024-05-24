package knu.dong.onedayonebaek.domain

import jakarta.persistence.*


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
    val groups: List<ContainGroup> = ArrayList()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}