package ua.good.data.repositories

import androidx.lifecycle.MutableLiveData
import ua.good.model.Notification
import ua.good.notifications.INotificationsUtil

interface INotificationsManager {
    val lastNotificationLiveData: MutableLiveData<Notification>
    fun newNotificationCame(item: Notification)
}

internal class NotificationsManager(private val notificationsHelper: INotificationsUtil) : INotificationsManager {
    /**
     * Второй вариант реализовать через PublishSubject или flow
     */
    override val lastNotificationLiveData = MutableLiveData<Notification>()

    /**
     * Еще детали в notifications.md
     */
    override fun newNotificationCame(item: Notification) {
        notificationsHelper.showNotification(item)
        lastNotificationLiveData.postValue(item)
    }
}
