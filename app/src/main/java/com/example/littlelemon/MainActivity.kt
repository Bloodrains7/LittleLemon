package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.littlelemon.data.model.MenuItemEntity
import com.example.littlelemon.data.model.MenuItemNetwork
import com.example.littlelemon.ui.navigation.NavigationComposable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationComposable()
        }
    }
}

private fun mapNetworkToEntity(networkItems: List<MenuItemNetwork>): List<MenuItemEntity> {
    return networkItems.map {
        MenuItemEntity(
            id = it.id,
            title = it.title,
            description = it.description,
            price = it.price,
            image = it.image
        )
    }
}
