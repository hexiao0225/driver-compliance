package com.compliance.model

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "drivers")
data class Driver(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val name: String = "",

    val email: String? = null,

    @Column(name = "created_at")
    val createdAt: OffsetDateTime = OffsetDateTime.now()
)
