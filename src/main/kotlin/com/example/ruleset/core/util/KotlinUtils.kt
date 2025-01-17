// Copyright 2023 Nacho Lopez
// SPDX-License-Identifier: Apache-2.0
package com.example.ruleset.core.util

import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtElement
import java.util.Locale

fun <T> T.runIf(
    value: Boolean,
    block: T.() -> T,
): T = if (value) block() else this

fun <T, R> T.runIfNotNull(
    value: R?,
    block: T.(R) -> T,
): T = value?.let { block(it) } ?: this

fun <T, R> Sequence<T>.mapIf(
    condition: (T) -> Boolean,
    transform: (T) -> R,
): Sequence<R> = mapNotNull { if (condition(it)) transform(it) else null }

fun <F, S, T : Pair<F, S>> Sequence<T>.mapFirst() = map { it.first }

fun <F, S, T : Pair<F, S>> Sequence<T>.mapSecond() = map { it.second }

operator fun FqName.plus(other: String): FqName = FqName(asString() + "." + other)

operator fun FqName.plus(other: FqName): FqName = if (isRoot) other else plus(other.asString())

fun String?.matchesAnyOf(patterns: Sequence<Regex>): Boolean {
    if (isNullOrEmpty()) return false
    for (regex in patterns) {
        if (matches(regex)) return true
    }
    return false
}

fun Set<String>.joinToRegexOrNull(): Regex? = if (isEmpty()) null else joinToRegex()

fun Set<String>.joinToRegex(): Regex =
    Regex(
        joinToString(
            separator = "|",
            prefix = "(",
            postfix = ")",
        ),
    )

fun String.toCamelCase() =
    split('_').joinToString(
        separator = "",
        transform = { original ->
            original.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
        },
    )

fun String.toSnakeCase() = replace(humps, "_").lowercase(Locale.getDefault())

private val humps by lazy { "(?<=.)(?=\\p{Upper})".toRegex() }

val KotlinScopeFunctions = setOf("with", "apply", "run", "also", "let")
val KotlinItObjectScopeFunctions = setOf("let", "also")

fun <T : KtElement> Sequence<T>.uniquePairs(): Sequence<Pair<T, T>> =
    sequence {
        val list = toList()
        for (i in list.indices) {
            for (j in i + 1 until list.size) {
                yield(Pair(list[i], list[j]))
            }
        }
    }