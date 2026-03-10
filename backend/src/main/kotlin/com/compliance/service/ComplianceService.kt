package com.compliance.service

import com.compliance.model.Driver
import com.compliance.repository.DocumentRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.UUID

enum class ComplianceStatus { VALID, EXPIRING, EXPIRED }

@Service
class ComplianceService(private val documentRepository: DocumentRepository) {

    fun computeStatus(driver: Driver): ComplianceStatus {
        val docs = documentRepository.findByDriverId(driver.id)
        val license = docs.find { it.documentType.code == "drivers_license" }
            ?: return ComplianceStatus.EXPIRED

        val expiration = license.expirationDate ?: return ComplianceStatus.VALID
        val today = LocalDate.now()

        return when {
            expiration < today -> ComplianceStatus.EXPIRED
            expiration < today.plusDays(30) -> ComplianceStatus.EXPIRING
            else -> ComplianceStatus.VALID
        }
    }
}
