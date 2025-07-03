# Kotlin Design Patterns

This repository contains Kotlin implementations of various **design patterns**, categorized as per the **Gang of Four**
classification: **Creational**, **Structural**, and **Behavioral**.

---

## Creational Patterns

Creational patterns provide mechanisms for object creation that increase flexibility and reuse of existing code.

| File                        | Pattern             | Description                                                                      |
|-----------------------------|---------------------|----------------------------------------------------------------------------------|
| `AbstractFactory.kt`        | Abstract Factory    | Creates families of related objects without specifying their concrete classes.   |
| `ClassicBuilder.kt`         | Builder (Classic)   | Constructs complex objects step-by-step with a fluent API (Java-style).          |
| `DSLBuilder.kt`             | Builder (DSL)       | Kotlin DSL-style builder for more expressive object construction.                |
| `Factory.kt`                | Factory Method      | Creates objects without exposing the instantiation logic to the client.          |
| `KotlinIdiomaticBuilder.kt` | Idiomatic Builder   | Uses Kotlin's data class, named arguments, and defaults as a simplified builder. |
| `LazyInitialization.kt`     | Lazy Initialization | Demonstrates Kotlin's `lazy {}` delegate for deferred, cached computation.       |
| `Prototype.kt`              | Prototype           | Clones existing objects instead of creating new ones from scratch.               |
| `Sigleton.kt`               | Singleton           | Ensures a class has only one instance, and provides global access to it.         |

---

## Structural Patterns

Structural patterns focus on how classes and objects are composed to form larger structures.

| File | Pattern | Description |
|------|---------|-------------|

---

## Behavioral Patterns

Behavioral patterns are concerned with the communication between objects.

| File | Pattern | Description |
|------|---------|-------------|

---