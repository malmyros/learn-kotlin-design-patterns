package creational

/*
What Is the Factory Pattern?

The Factory Pattern defines an interface for creating objects,
but lets subclasses decide which class to instantiate.

When to use it in Fintech:
- You need to abstract the creation of notification channels (e.g., SMS, email, push)
- You want to decouple the logic that generates events (e.g., payment success) from how the event is delivered
- You want to support easy extension for more channels like WhatsApp or in-app alerts
*/

fun main() {

    val notificationFactory = NotificationFactory()

    // Simulate a payment success event
    val userId = "e35ec78a-bd3e-4185-af57-682f37530d13"
    val paymentEvent = PaymentEvent(userId = userId, channel = NotificationType.EMAIL)
    val notification = notificationFactory.createNotification(paymentEvent.channel)

    notification.notifyUser("Payment of £250 was successful for user ${paymentEvent.userId}")
}

data class PaymentEvent(
    val userId: String,
    val channel: NotificationType
)

enum class NotificationType {
    EMAIL, SMS, PUSH
}

interface Notification {
    fun notifyUser(message: String)
}

class EmailNotification : Notification {
    override fun notifyUser(message: String) {
        println("Email sent with message: $message")
    }
}

class SMSNotification : Notification {
    override fun notifyUser(message: String) {
        println("SMS sent with message: $message")
    }
}

class PushNotification : Notification {
    override fun notifyUser(message: String) {
        println("Push sent with message: $message")
    }
}

class NotificationFactory {
    fun createNotification(type: NotificationType): Notification {
        return when (type) {
            NotificationType.EMAIL -> EmailNotification()
            NotificationType.SMS -> SMSNotification()
            NotificationType.PUSH -> PushNotification()
        }
    }
}