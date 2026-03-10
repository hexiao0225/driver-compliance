package com.compliance.model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "document_types")
data class DocumentType(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(unique = true, nullable = false)
    val code: String = "",

    @Column(nullable = false)
    val name: String = ""
)
