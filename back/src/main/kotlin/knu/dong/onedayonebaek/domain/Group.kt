package knu.dong.onedayonebaek.domain

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
@Table(name = "study_groups")
class Group (
    @Column(nullable = false)
    var name: String,

    @ColumnDefault("false")
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    val isPrivate: Boolean = false,

    @Column
    val password: String?,

    @Column(nullable = false)
    val inviteCode: String,

    @OneToMany(mappedBy = "group")
    val users: MutableSet<ContainGroup> = mutableSetOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val owner: User

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}