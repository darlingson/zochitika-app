package com.codeshinobi.zochitika.repositories

import com.codeshinobi.zochitika.models.ProductDto

interface ProductRepository {
    fun getProducts(): List<ProductDto>
}

class ProductRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
) : ProductRepository {
    override suspend fun getProducts(): List<ProductDto> {
        val result = client.postgrest["products"]
            .select().decodeList<ProductDto>()
        // Handle result data for next step
        return result
    }
}
