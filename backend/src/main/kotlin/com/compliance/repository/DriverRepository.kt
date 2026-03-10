package com.compliance.repository

import com.compliance.model.Driver
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DriverRepository : JpaRepository<Driver, UUID>
