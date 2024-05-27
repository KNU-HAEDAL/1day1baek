package knu.dong.onedayonebaek.domain

import jakarta.persistence.*
import java.time.LocalDate


@Entity
@Table(name = "problems")
class Problem(
    @Column(nullable = false)
    var solvedDate: LocalDate,

    @Column(nullable = false, unique = true)
    var commitUrl: String,

    var problemRank: String,

    @Column(nullable = false)
    var title: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User
    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}