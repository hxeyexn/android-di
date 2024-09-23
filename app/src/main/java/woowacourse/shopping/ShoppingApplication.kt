package woowacourse.shopping

import android.app.Application
import com.woowacourse.di.DependencyInjector
import com.woowacourse.di.InMemory
import com.woowacourse.di.RoomDB
import com.woowacourse.di.Singleton
import woowacourse.shopping.data.ShoppingDatabase
import woowacourse.shopping.model.CartRepository
import woowacourse.shopping.model.ProductRepository

class ShoppingApplication : Application() {
    private val db by lazy { ShoppingDatabase.initialize(applicationContext) }
    private val cartProductDao by lazy { db.cartProductDao() }

    override fun onCreate() {
        super.onCreate()
        dependencyInjector = DependencyInjector()
        initialize()
    }

    private fun initialize() {
        registerProductRepository()
        registerCartRepository()
    }

    private fun registerProductRepository() {
        dependencyInjector.addInstance(
            ProductRepository::class,
            RepositoryModule.provideProductRepository(),
            InMemory::class,
        )
    }

    private fun registerCartRepository() {
        dependencyInjector.addInstance(
            CartRepository::class,
            RepositoryModule.provideCartRepository(cartProductDao),
            RoomDB::class,
            Singleton::class,
        )
        dependencyInjector.addInstance(
            CartRepository::class,
            RepositoryModule.provideCartInMemoryRepository(),
            InMemory::class,
            Singleton::class,
        )
    }

    companion object {
        lateinit var dependencyInjector: DependencyInjector
            private set
    }
}
