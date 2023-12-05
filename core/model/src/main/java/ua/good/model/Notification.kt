package ua.good.model

data class Notification(
    val title: String = "",
    val text: String = "",
    val testData: String = ""
) {
    // fun toNotificationState() = NotificationState(title, text, testData)
}
