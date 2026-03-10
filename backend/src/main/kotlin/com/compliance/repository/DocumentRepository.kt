package com.compliance.repository

import com.compliance.model.Document
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DocumentRepository : JpaRepository<Document, UUID> {
    fun findByDriverId(driverId: UUID): List<Document>
}
