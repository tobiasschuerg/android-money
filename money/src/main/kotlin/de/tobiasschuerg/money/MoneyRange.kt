package de.tobiasschuerg.money

/**
 * Represents a closed range of [Money] values in the same currency.
 * Useful for budget checks and threshold validations.
 *
 * Example:
 * ```
 * val budget = Money(100, EUR)..Money(500, EUR)
 * Money(250, EUR) in budget // true
 * ```
 */
data class MoneyRange(
    override val start: Money,
    override val endInclusive: Money,
) : ClosedRange<Money>
