# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

An Android library (Kotlin) for money and currency handling with BigDecimal-based arithmetic. Published via JitPack as `com.github.tobiasschuerg:android-money`. Package: `de.tobiasschuerg.money`.

## Build Commands

```bash
./gradlew build              # Build everything (library + sample)
./gradlew money:build        # Build library only
./gradlew money:test         # Run unit tests
./gradlew ktlintCheck        # Run ktlint
./gradlew detekt             # Run detekt static analysis
```

Uses Gradle 7.3.3, Kotlin 1.7.20, compileSdk 32, minSdk 14.

## Modules

- **money** - Core library module (`money/src/main/java/de/tobiasschuerg/money/`)
- **sample** - Demo Android app (`sample/`)

## Architecture

All classes live in `de.tobiasschuerg.money`:

- **Money** - Immutable value type wrapping `BigDecimal` amount + `Currency`. Supports operator overloading (`+`, `-`, `*`, `/`), comparison, and currency conversion. Arithmetic between two `Money` instances requires matching currency codes (no implicit conversion).
- **Currency** - Data class with `CurrencyCode`, name, and exchange rate (as `BigDecimal`). Conversion rates are relative: `conversionTo()` computes cross-rate via `1/thisRate * targetRate`.
- **MoneyList** - `MutableList<Money>` implementation that maintains a running sum. Supports `autoConvert` mode for mixed-currency lists.
- **MoneyUtils** - Extension functions: `Collection<Money>.sum()` (same-currency) and `Collection<Money>.sum(currency)` (with auto-conversion).
- **MoneyConfig** - Global singleton controlling `scale` for BigDecimal comparisons (default: 10).
- **Money.ZERO** / **Currency.NONE** - Sentinel values used for zero-amount identity in arithmetic.

## Code Style

Enforced by ktlint and detekt (configured with `buildUponDefaultConfig = true`). Both run as Gradle plugins on all subprojects.
