# Kotlin Design Patterns

This repository contains Kotlin implementations of various **design patterns**, categorized as per the **Gang of Four**
classification: **Creational**, **Structural**, and **Behavioral**.

---

## Creational Patterns

Creational patterns provide mechanisms for object creation that increase flexibility and reuse of existing code.

| File                        | Pattern             | Description (Fintech Context)                                                                 |
|-----------------------------|---------------------|-----------------------------------------------------------------------------------------------|
| `AbstractFactory.kt`        | Abstract Factory    | Creates families of related payment components (e.g., cards, fees, limits) per card program.  |
| `ClassicBuilder.kt`         | Builder (Classic)   | Builds complex domain models step-by-step (e.g., a card product or customer onboarding form). |
| `DSLBuilder.kt`             | Builder (DSL)       | Defines a fintech-specific DSL to construct payment requests or business rules declaratively. |
| `Factory.kt`                | Factory Method      | Delegates object creation (e.g., for transaction handlers or notification channels).          |
| `KotlinIdiomaticBuilder.kt` | Idiomatic Builder   | Uses Kotlin’s features to build domain objects like `Transaction` or `UserProfile` fluently.  |
| `LazyInitialization.kt`     | Lazy Initialization | Defers loading of expensive objects (e.g., exchange rates, fraud models) until needed.        |
| `Prototype.kt`              | Prototype           | Clones pre-configured payment workflows or risk profiles to avoid reconfiguration overhead.   |
| `Sigleton.kt`               | Singleton           | Ensures one instance of critical components (e.g., audit logger, rate limiter) exists.        |

---

## Structural Patterns

Structural patterns focus on how classes and objects are composed to form larger structures.

| File           | Pattern   | Description                                                                                                                                                   |
|----------------|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `Adapter.kt`   | Adapter   | Allows integration of incompatible interfaces. Wraps a legacy or third-party payment service to match your system’s expected `PaymentProcessor` interface.    |
| `Bridge.kt`    | Bridge    | Decouples abstraction from implementation. Useful for separating card types (debit, credit) from card issuers (e.g., Visa, Mastercard).                       |
| `Composite.kt` | Composite | Treats individual and grouped objects uniformly. Can model a fee engine with basic fees and nested fee bundles.                                               |
| `Decorator.kt` | Decorator | Adds behavior dynamically without modifying original logic. Common in fraud detection or logging around payments.                                             |
| `Facade.kt`    | Facade    | Provides a unified interface over complex subsystems. Can simplify interaction with a core banking system composed of ledgers, limits, and customer profiles. |
| `Flyweight.kt` | Flyweight | Reduces memory usage by sharing data. Ideal for optimizing shared card product configurations or BIN metadata.                                                |
| `Proxy.kt`     | Proxy     | Adds access control or lazy initialization to real objects. Can be used to restrict or cache calls to a remote KYC verification service.                      |

---

## Behavioral Patterns

Behavioral patterns are concerned with the communication between objects.

| File                       | Pattern                 | Description                                                                                                                                                  |
|----------------------------|-------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `ChainOfResponsibility.kt` | Chain of Responsibility | Passes a request through a chain of handlers. Useful for transaction validation pipelines (e.g., balance check → fraud check → limits check).                |
| `Command.kt`               | Command                 | Encapsulates a request as an object. Useful for storing, auditing, or retrying actions like reversing a payment or retrying a failed payout.                 |
| `Interpreter.kt`           | Interpreter             | Implements a simple language or rule engine. Can parse and evaluate transaction rules or fee calculation expressions.                                        |
| `Iterator.kt`              | Iterator                | Provides a way to access elements of a collection sequentially. Useful for iterating over batches of transactions or card events.                            |
| `Mediator.kt`              | Mediator                | Centralizes complex communication between objects. Useful in orchestrating workflows like KYC where multiple services interact (doc scan, OCR, fraud score). |
| `Memento.kt`               | Memento                 | Captures and restores object state. Useful for rollback scenarios or storing snapshots of account state.                                                     |
| `Observer.kt`              | Observer                | Notifies subscribers when something changes. Common in event-driven systems — e.g., notify fraud team on suspicious activity or publish events to Kafka.     |
| `State.kt`                 | State                   | Allows an object to alter its behavior based on internal state. Useful for modeling card lifecycle (e.g., Active, Frozen, Closed).                           |
| `Strategy.kt`              | Strategy                | Encapsulates interchangeable algorithms. Often used for dynamic fee strategies, interest calculation, or fraud detection logic.                              |
| `TemplateMethod.kt`        | Template Method         | Defines the skeleton of an operation while letting subclasses override certain steps. Useful for transaction processing flows across payment types.          |
| `Visitor.kt`               | Visitor                 | Adds new behavior to existing class structures without modifying them. Can be used for reporting or extracting audit data from complex domain models.        |