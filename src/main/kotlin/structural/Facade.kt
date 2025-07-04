package structural

/*
What Is the Facade Pattern?

The Facade Pattern provides a simplified, unified interface
to a complex system of classes, libraries, or subsystems.

It hides internal complexity and decouples clients
from implementation details.

When to use it:
- You have a system with many interdependent components
- You want to simplify and standardize how clients interact with those components
- You want to improve onboarding and maintainability by exposing a clean API

Fintech Example:
- Managing a card lifecycle (activate, ship, freeze, close) through one interface
- Wrapping complex payment initiation and confirmation flows
- Simplifying interactions with a core banking or transaction processing subsystem
*/

fun main() {
    val cardLifecycleFacade = CardLifecycleFacade(
        cardStatusService = CardStatusService(),
        cardShipmentService = CardShipmentService(),
        cardAuditService = CardAuditService(),
    )

    val cardId = "afb06b86-a2e9-48b6-9922-f78a9bd83cfe"
    val address = "42 Fintech Street"

    cardLifecycleFacade.issueNewCard(cardId, address)
    cardLifecycleFacade.freezeCard(cardId)
    cardLifecycleFacade.closeCard(cardId)
}

class CardStatusService {
    fun activate(cardId: String) = println("Activating card $cardId")
    fun freeze(cardId: String) = println("Card $cardId is frozen")
    fun close(cardId: String) = println("Card $cardId is closed")
}

class CardShipmentService {
    fun dispatch(cardId: String, address: String) = println("Card $cardId shipped to $address")
}

class CardAuditService {
    fun logEvent(cardId: String, event: String) = println("AUDIT: [$cardId] - $event")
}

class CardLifecycleFacade(
    private val cardStatusService: CardStatusService,
    private val cardShipmentService: CardShipmentService,
    private val cardAuditService: CardAuditService,
) {

    fun issueNewCard(cardId: String, address: String) {
        cardStatusService.activate(cardId)
        cardShipmentService.dispatch(cardId, address)
        cardAuditService.logEvent(cardId, "Card issued and dispatched")
    }

    fun freezeCard(cardId: String) {
        cardStatusService.freeze(cardId)
        cardAuditService.logEvent(cardId, "Card frozen")
    }

    fun closeCard(cardId: String) {
        cardStatusService.close(cardId)
        cardAuditService.logEvent(cardId, "Card closed")
    }
}