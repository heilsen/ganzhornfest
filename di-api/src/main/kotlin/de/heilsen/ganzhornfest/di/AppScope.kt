package de.heilsen.ganzhornfest.di

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlin.reflect.KProperty

@JvmInline
value class AppScope internal constructor(
    val component: Any
)

val Context.appScope: AppScope
    get() {
        val component = (applicationContext as AppComponentProvider).appComponent
        return AppScope(component)
    }

@Suppress("UNCHECKED_CAST")
fun <ENTRYPOINT : Any> AppScope.entryPoint(): ENTRYPOINT =
    component as? ENTRYPOINT ?: error("entrypoint not found")

inline operator fun <reified ENTRYPOINT : Any> AppScope.getValue(
    thisRef: Any?,
    property: KProperty<*>
): ENTRYPOINT = entryPoint()

@Composable
fun rememberAppScope(): AppScope {
    val context = LocalContext.current
    return remember { context.appScope }
}