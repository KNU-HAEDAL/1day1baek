package knu.dong.onedayonebaek.domain

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "study_groups")
class Group (
    @Column(nullable = false)
    var name: String,

    @ColumnDefault("false")
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    val isPrivate: Boolean = false,

    @Column
    val inviteCode: String,

    @OneToMany(mappedBy = "group")
    val users: List<ContainGroup> = ArrayList()

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}