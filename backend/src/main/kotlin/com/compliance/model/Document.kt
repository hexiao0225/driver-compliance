package com.compliance.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "documents")
data class Document(
    @Id
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    val driver: Driver = Driver(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id", nullable = false)
    val documentType: DocumentType = DocumentType(),

    @Column(name = "issued_date", nullable = false)
    val issuedDate: LocalDate = LocalDate.now(),

    @Column(name = "expiration_date")
    val expirationDate: LocalDate? = null,

    @Column(name = "created_at")
    val createdAt: OffsetDateTime = OffsetDateTime.now()
)
