package behavioural

import java.math.BigDecimal

/*
What Is the Interpreter Pattern?

The Interpreter Pattern defines a way to evaluate grammar or expressions by representing them as an abstract syntax tree
and evaluating them step-by-step using a common interface.

When to use it:
- You want to interpret a custom DSL or expression language
- You need to let non-developers define logic (e.g., compliance rules)
- You want to make logic configurable without redeploying code

Fintech Example:
- A rule engine that evaluates payment conditions like "amount > 1000 AND country == 'UK'"
*/

fun main() {

    val ruleText = """amount > 1000 AND country == UK"""
    val paymentRequest = PaymentRequest(
        userId = "3243537d-47fd-4e5b-aea8-45b0f42f60e0",
        amount = BigDecimal(1_200),
        country = "UK"
    )

    val ruleParser = RuleParser()
    val expression = ruleParser.parse(ruleText)

    val result = expression.interpret(paymentRequest)
    println("Rule evaluation result: $result")
}

interface Expression {
    fun interpret(context: PaymentRequest): Boolean
}

class GreaterThan(
    private val field: String,
    private val value: BigDecimal,
) : Expression {
    override fun interpret(context: PaymentRequest): Boolean {
        val fieldValue = when (field) {
            "amount" -> context.amount
            else -> BigDecimal.ZERO
        }
        return fieldValue > value
    }
}

class LessThan(
    private val field: String,
    private val value: BigDecimal,
) : Expression {
    override fun interpret(context: PaymentRequest): Boolean {
        val fieldValue = when (field) {
            "amount" -> context.amount
            else -> BigDecimal.ZERO
        }
        return fieldValue < value
    }
}

class Equals(
    private val field: String,
    private val expected: String,
) : Expression {
    override fun interpret(context: PaymentRequest): Boolean {
        val fieldValue = when (field) {
            "country" -> context.country
            "userId" -> context.userId
            else -> ""
        }
        return fieldValue == expected
    }
}

class AndExpression(
    private val left: Expression,
    private val right: Expression,
) : Expression {
    override fun interpret(context: PaymentRequest): Boolean {
        return left.interpret(context) && right.interpret(context)
    }
}

class OrExpression(
    private val left: Expression,
    private val right: Expression,
) : Expression {
    override fun interpret(context: PaymentRequest): Boolean {
        return left.interpret(context) || right.interpret(context)
    }
}

class RuleParser {

    fun parse(rule: String): Expression {
        val tokens = rule.split(' ').toMutableList()
        var left: Expression = parseSimple(tokens.removeFirst(), tokens.removeFirst(), tokens.removeFirst())

        while (tokens.isNotEmpty()) {
            val operator = tokens.removeFirst()
            val field = tokens.removeFirst()
            val op = tokens.removeFirst()
            val value = tokens.removeFirst()

            val right = parseSimple(field, op, value)

            left = when (operator) {
                "AND" -> AndExpression(left, right)
                "OR" -> OrExpression(left, right)
                else -> throw IllegalArgumentException("Unexpected operator: $operator")
            }
        }

        return left
    }

    private fun parseSimple(field: String, op: String, value: String): Expression {
        return when (op) {
            ">" -> GreaterThan(field, value.toBigDecimal())
            "<" -> LessThan(field, value.toBigDecimal())
            "==" -> Equals(field, value.trim('"'))
            else -> throw IllegalArgumentException("Unexpected operator: $op")
        }
    }
}