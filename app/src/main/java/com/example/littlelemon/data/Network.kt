package com.example.littlelemon.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuNetwork(
    val menu: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("price")
    val price: String,
    @SerialName("image")
    val image: String,
    @SerialName("category")
    val category: String
)

fun MenuItemNetwork.toMenuItem() = MenuItem(
    id = id,
    title = title,
    description = description,
    price = price,
    image = image,
    category = category
)

fun List<MenuItemNetwork>.toMenuItemList() = map { it.toMenuItem() }
