package behavioural

/*
What Is the Mediator Pattern?

The Mediator Pattern centralizes communication between components to reduce direct dependencies.
Each component interacts with a single mediator instead of many others.

When to use it:
- You want to decouple tightly-connected objects
- You have many components that interact in a complex workflow
- You want to centralize coordination logic in one place

Fintech Example:
- A user onboarding system where KYC, Risk, and Compliance modules
  communicate through a mediator to trigger each other in sequence.
*/

fun main() {

    val userId = "e4a975b2-764a-4c7f-9aee-a54eb494ea28"
    val mediator = OnboardingMediator()

    val kyc = KYCService(mediator)
    val risk = RiskService(mediator)
    val compliance = ComplianceService(mediator)

    mediator.register(kyc, risk, compliance)

    kyc.verify(userId)
}

interface OnboardingCoordinator {
    fun notify(sender: OnboardingComponent, event: String, userId: String)
}

abstract class OnboardingComponent(protected val mediator: OnboardingCoordinator)


class KYCService(mediator: OnboardingCoordinator) : OnboardingComponent(mediator) {
    fun verify(userId: String) {
        println("KYC passed for $userId")
        mediator.notify(this, "kyc_complete", userId)
    }
}

class RiskService(mediator: OnboardingCoordinator) : OnboardingComponent(mediator) {
    fun score(userId: String) {
        println("Risk score completed for $userId")
        mediator.notify(this, "risk_complete", userId)
    }
}

class ComplianceService(mediator: OnboardingCoordinator) : OnboardingComponent(mediator) {
    fun screen(userId: String) {
        println("Compliance screen completed for $userId")
        mediator.notify(this, "compliance_complete", userId)
    }
}

class OnboardingMediator : OnboardingCoordinator {
    private lateinit var kyc: KYCService
    private lateinit var risk: RiskService
    private lateinit var compliance: ComplianceService

    fun register(kyc: KYCService, risk: RiskService, compliance: ComplianceService) {
        this.kyc = kyc
        this.risk = risk
        this.compliance = compliance
    }

    override fun notify(sender: OnboardingComponent, event: String, userId: String) {
        when (event) {
            "kyc_complete" -> risk.score(userId)
            "risk_complete" -> compliance.screen(userId)
            "compliance_complete" -> println("Onboarding completed for $userId")
        }
    }
}