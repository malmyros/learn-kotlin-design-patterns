package creational

/*
What Is the DSL Builder Pattern?

The DSL Builder leverages Kotlin’s lambda-with-receiver feature to build
objects in a domain-specific, declarative style.

When to use it:
- When you want to make configuration code expressive and readable
- When modeling business rules, workflows, or UI/state definitions
- When you want to provide a fluent syntax that feels like natural language

Fintech Example:
- Building compliance or KYC rules in a readable format that business
  and engineering teams can both understand and audit.
*/

fun main() {

    val complianceRule = complianceRule {
        name("Age Check")
        description("Reject applicants over 18 years old")
        severity("HIGH")
        condition("applicant.age > 18")
    }

    println("Rule '${complianceRule.name}' - ${complianceRule.description} [${complianceRule.severity}]")
    println("Condition: ${complianceRule.condition}")
}

class ComplianceRule(
    var name: String = "",
    var description: String = "",
    var severity: String = "",
    var condition: String = "",
)

class ComplianceRuleBuilder {
    private val complianceRule = ComplianceRule()

    fun name(value: String) = apply { complianceRule.name = value }
    fun description(value: String) = apply { complianceRule.description = value }
    fun severity(value: String) = apply { complianceRule.severity = value }
    fun condition(value: String) = apply { complianceRule.condition = value }

    fun build() = complianceRule
}

fun complianceRule(block: ComplianceRuleBuilder.() -> Unit): ComplianceRule {
    return ComplianceRuleBuilder().apply(block).build()
}