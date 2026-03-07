# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

A Kotlin/JVM library for money and currency handling with BigDecimal-based arithmetic. Published via JitPack as `com.github.tobiasschuerg:android-money`. Package: `de.tobiasschuerg.money`.

## Build Commands

```bash
./gradlew build              # Build everything (library + sample)
./gradlew money:build        # Build library only
./gradlew money:test         # Run unit tests
./gradlew ktlintCheck        # Run ktlint
./gradlew ktlintFormat       # Auto-fix ktlint issues
./gradlew detekt             # Run detekt static analysis
```

Uses Gradle 9.3.1, Kotlin 2.2.10, JVM toolchain 17.

## Modules

- **money** — Core Kotlin/JVM library (`money/src/main/kotlin/de/tobiasschuerg/money/`)
- **sample** — Demo Android app (`sample/`), compileSdk 36

## Architecture

All classes live in `de.tobiasschuerg.money`:

- **Money** — Immutable value type wrapping `BigDecimal` amount + `Currency`. Supports operator overloading (`+`, `-`, `*`, `/`), comparison, and currency conversion via `ExchangeRate`. Arithmetic between two `Money` instances requires matching currencies.
- **Currency** — Pure identity type with `CurrencyCode` and name. Does not hold exchange rates.
- **ExchangeRate** — Models a directional conversion rate between two currencies. Supports `inverse()` for reverse conversion.
- **MoneyList** — `MutableList<Money>` implementation that maintains a running sum. All entries must share the same currency.
- **MoneyUtils** — Extension functions: `Collection<Money>.sum()`, `Money.toFloat()`, `Money.toDouble()`.
- **MoneyConfig** — Global constant `SCALE` for BigDecimal comparisons (default: 10).
- **Currencies** — Predefined currency constants (EURO, USDOLLAR, BITCOIN).

## Code Style

Enforced by ktlint and detekt (configured with `buildUponDefaultConfig = true`). Both run as Gradle plugins on all subprojects.
