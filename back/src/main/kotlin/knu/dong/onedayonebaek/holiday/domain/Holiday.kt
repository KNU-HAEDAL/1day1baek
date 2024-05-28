package knu.dong.onedayonebaek.holiday.domain

import jakarta.persistence.*
import knu.dong.onedayonebaek.group.domain.Group
import java.time.LocalDate

@Entity
@Table(name = "holidays", uniqueConstraints = [UniqueConstraint(columnNames = ["date", "group_id"])])
class Holiday(
    @Column(nullable = false)
    val date: LocalDate,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    val group: Group
) {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}