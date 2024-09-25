package woowacourse.shopping

import android.content.Context
import com.woowacourse.di.ActivityScope
import com.woowacourse.di.InMemory
import com.woowacourse.di.RoomDB
import com.woowacourse.di.Singleton
import com.woowacourse.di.ViewModelScope
import woowacourse.shopping.data.CartProductDao
import woowacourse.shopping.model.CartRepository
import woowacourse.shopping.model.ProductRepository
import woowacourse.shopping.ui.cart.DateFormatter

class ComponentRegister(
    private val context: Context,
    private val cartProductDao: CartProductDao,
) {
    fun initialize() {
        registerDateFormatter()
        registerProductRepository()
        registerCartRepository()
    }

    private fun registerDateFormatter() {
        ShoppingApplication.dependencyInjector.addInstance(
            DateFormatter::class,
            DIModule.provideDateFormatter(context),
            scope = ActivityScope::class,
        )
    }

    private fun registerProductRepository() {
        ShoppingApplication.dependencyInjector.addInstance(
            ProductRepository::class,
            DIModule.provideProductRepository(),
            qualifier = InMemory::class,
            scope = ViewModelScope::class,
        )
    }

    private fun registerCartRepository() {
        ShoppingApplication.dependencyInjector.addInstance(
            CartRepository::class,
            DIModule.provideCartRepository(cartProductDao),
            qualifier = RoomDB::class,
            scope = Singleton::class,
        )
        ShoppingApplication.dependencyInjector.addInstance(
            CartRepository::class,
            DIModule.provideCartInMemoryRepository(),
            qualifier = InMemory::class,
            scope = Singleton::class,
        )
    }
}
