package knu.dong.onedayonebaek.domain

import jakarta.persistence.*

@Entity
@Table(name = "contain_groups")
class ContainGroup (
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne
    @JoinColumn(name = "group_id")
    var group: Group
)  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}