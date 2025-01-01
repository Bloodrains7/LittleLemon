package com.example.littlelemon.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase


val SampleData = listOf(
    MenuItem(
        id = 1,
        title = "Greek Salad",
        description = "The famous greek salad of crispy lettuce, peppers, olives, our Chicago.",
        price = "10",
        image = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true",
        category = "starters"
    ),
    MenuItem(
        id = 2,
        title = "Lemon Desert",
        description = "Traditional homemade Italian Lemon Ricotta Cake.",
        price = "10",
        image = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/lemonDessert%202.jpg?raw=true",
        category = "desserts"
    ),
    MenuItem(
        id = 3,
        title = "Grilled Fish",
        description = "Our Bruschetta is made from grilled bread that has been smeared with garlic and seasoned with salt and olive oil.",
        price = "10",
        image = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/grilledFish.jpg?raw=true",
        category = "mains"
    ),
    MenuItem(
        id = 4,
        title = "Pasta",
        description = "Penne with fried aubergines, cherry tomatoes, tomato sauce, fresh chili, garlic, basil & salted ricotta cheese.",
        price = "10",
        image = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/pasta.jpg?raw=true",
        category = "mains"
    ),
    MenuItem(
        id = 5,
        title = "Bruschetta",
        description = "Oven-baked bruschetta stuffed with tomatoes and herbs.",
        price = "10",
        image = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/bruschetta.jpg?raw=true",
        category = "starters"
    )
)

@Entity(tableName = "menu_items")
data class MenuItem(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

@Dao
interface MenuDao {

    @Query("SELECT * FROM menu_items")
    fun getAllMenuItems(): LiveData<List<MenuItem>>

    @Insert
    fun insertAllMenuItems(menuItems: List<MenuItem>)

    @Query("SELECT (SELECT COUNT(*) FROM menu_items) == 0")
    fun isEmpty(): Boolean

    @Query("DELETE FROM menu_items")
    fun deleteAllMenuItems()
}

@Database(entities = [MenuItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao

    companion object {
        const val DATABASE_NAME = "little_lemon_database"

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val temp = INSTANCE
            if (temp != null) {
                return temp
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}