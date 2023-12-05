package ua.artem.template.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.good.utils.base.BaseModel
import ua.good.utils.base.ViewState

/**
 * Хранить state сразу в BaseViewModel кажется хорошая идея, получаем более чистый код.
 * По надобности дополнительные поля использовать уже в реализации.
 *
 * Сохранение состояния:
 * ViewModel - переживает поворот экрана и другие изменения конфигурации
 * SavedStateHandle - нужен, что бы справиться с завершением процесса системой (после сворачивания)
 * EditText - значения по умолчанию сохраняются
 *
 * Конкретные ViewModel:
 * RepositoriesModel - SavedStateHandle использовать для сохранения state нужно, так как после поворота
 * и восстановления данные грузятся из базы в этом случае
 * LoginModel - если приложение было убито системой во время авторизации, сохранять состояние нет
 * смысла, соединение разрывается, EditTexts сохраняются сами
 *
 * Вывод:
 * Использовать SavedStateHandle для STATE не нужно, так как не всегда нужно сохранение состояния
 */
@Suppress("unused")
abstract class BaseViewModel<STATE : ViewState> : BaseModel() {

    private val mState = MutableLiveData<STATE>()
    val state: LiveData<STATE>
        get() {
            return mState
        }
}
