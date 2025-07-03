package creational

fun main() {

    val notificationFactory = NotificationFactory()
    val emailNotification = notificationFactory.createNotification("email")
    emailNotification?.notifyUser()
}

interface Notification {
    fun notifyUser()
}

class EmailNotification : Notification {
    override fun notifyUser() {
        println("Sending an email notification")
    }
}

class SMSNotification : Notification {
    override fun notifyUser() {
        println("Sending an SMS notification")
    }
}

class PushNotification : Notification {
    override fun notifyUser() {
        println("Sending an Push notification")
    }
}

class NotificationFactory {
    fun createNotification(type: String): Notification? {
        return when (type) {
            "email" -> EmailNotification()
            "sms" -> SMSNotification()
            "push" -> PushNotification()
            else -> null
        }
    }
}