package knu.dong.onedayonebaek.holiday.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "holidays")
class Holiday(@Column(nullable = false, unique = true) var date: LocalDate) {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}