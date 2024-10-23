package com.demo.service

import com.demo.dto.CreateOrderRequest
import com.demo.persistence.entity.Audit
import com.demo.persistence.repository.AuditRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.collections.LinkedHashMap

@Service
class DocumentDBServiceImpl @Autowired constructor(
    private val auditRepository: AuditRepository
) : DocumentDBService {

    override fun insertDocument(uuid: String, payload: CreateOrderRequest): Boolean {
        try {
            val structuredPayload = removeDotsInKeys(convertOrderToMap(payload))
            val entity = Audit(id = uuid, rawPayload = structuredPayload)
            auditRepository.save(entity)
            return true
        } catch (e: Exception) {
            log.error("Exception: Unable to insert document. Exception {}, payload {}", e.message, payload)
            return false
        }
    }

    // Convert CreateOrderRequest to a map of values
    private fun convertOrderToMap(createOrderRequest: CreateOrderRequest): LinkedHashMap<String, Any> {
        val map = LinkedHashMap<String, Any>()
        createOrderRequest.bookId.let { map["bookId"] = it }
        createOrderRequest.userId.let { map["userId"] = it }
        createOrderRequest.quantity.let { map["quantity"] = it }
        createOrderRequest.orderDate.let { map["orderDate"] = it }
        return map
    }

    // Replace dots in map keys with underscores
    private fun removeDotsInKeys(originalMap: LinkedHashMap<String, Any>): LinkedHashMap<String, Any> {
        val newMap = LinkedHashMap<String, Any>()
        for ((key, value) in originalMap) {
            val newKey = key.replace('.', '_')
            newMap[newKey] = value
        }
        return newMap
    }

    companion object {
        private val log = LoggerFactory.getLogger(DocumentDBServiceImpl::class.java)
    }
}