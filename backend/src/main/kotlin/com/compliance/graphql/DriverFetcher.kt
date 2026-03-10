package com.compliance.graphql

import com.compliance.model.Driver
import com.compliance.repository.DriverRepository
import com.compliance.service.ComplianceService
import com.compliance.service.ComplianceStatus
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import java.util.UUID

@DgsComponent
class DriverFetcher(
    private val driverRepository: DriverRepository,
    private val complianceService: ComplianceService
) {

    @DgsQuery
    fun drivers(): List<Map<String, Any?>> {
        return driverRepository.findAll().map { driver ->
            val status = complianceService.computeStatus(driver)
            mapOf(
                "id" to driver.id.toString(),
                "name" to driver.name,
                "email" to driver.email,
                "status" to status.name
            )
        }
    }

    @DgsMutation
    fun addDriver(
        @InputArgument name: String,
        @InputArgument email: String?
    ): Map<String, Any?> {
        val driver = driverRepository.save(
            Driver(id = UUID.randomUUID(), name = name, email = email)
        )
        return mapOf(
            "id" to driver.id.toString(),
            "name" to driver.name,
            "email" to driver.email,
            "status" to ComplianceStatus.EXPIRED.name
        )
    }
}
