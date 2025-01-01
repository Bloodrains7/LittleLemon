package com.example.littlelemon.repository

import android.content.Context
import com.example.littlelemon.client.ApiClient
import com.example.littlelemon.data.model.AppDatabase
import com.example.littlelemon.data.model.MenuItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AppRepository private constructor(
    private val apiClient: ApiClient,
    private val appDatabase: AppDatabase
) {

    companion object {
        private var INSTANCE: AppRepository? = null

        fun getInstance(
            context: Context,
            apiClient: ApiClient = ApiClient(),
            appDatabase: AppDatabase = AppDatabase.getDatabase(context)
        ): AppRepository {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = AppRepository(
                        apiClient = apiClient,
                        appDatabase = appDatabase
                    )
                }
            }
            return INSTANCE!!
        }
    }

    fun getMenuData() = flow {
        val localItems = appDatabase.menuDao().getAllMenuItems().first()

        if (localItems.isEmpty()) {
            val networkItems = apiClient.getMenuData()

            val localItemsMapped = networkItems.menu.map {
                MenuItemEntity(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    price = it.price,
                    image = it.image,
                    category = it.category
                )
            }

            appDatabase.menuDao().insertMenuItems(localItemsMapped)

            emit(localItemsMapped)
        } else {
            emit(localItems)
        }
    }.flowOn(Dispatchers.IO)
}