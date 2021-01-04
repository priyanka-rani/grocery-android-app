package com.myapp.grocerli.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.myapp.grocerli.data.CartItem
import com.myapp.grocerli.data.OrderItem
import com.myapp.grocerli.data.Product
import com.myapp.grocerli.data.Profile
import com.myapp.grocerli.workers.CartDatabaseWorker

@Database(
        entities = [
            Profile::class,
            Product::class,
            CartItem::class,
            OrderItem::class
        ],
        version = 10,
        exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao

    companion object {
        private const val DB_NAME = "cart_database"

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(
                            object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    val request = OneTimeWorkRequestBuilder<CartDatabaseWorker>().build()
                                    WorkManager.getInstance(context).enqueue(request)
                                }
                            }
                    )
                    .build()
        }
    }
}