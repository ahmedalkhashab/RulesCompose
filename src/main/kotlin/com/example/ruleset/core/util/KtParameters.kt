// Copyright 2024 Nacho Lopez
// SPDX-License-Identifier: Apache-2.0
package com.example.ruleset.core.util

import org.jetbrains.kotlin.psi.KtNullableType
import org.jetbrains.kotlin.psi.KtParameter

val KtParameter.isTypeNullable: Boolean
    get() = typeReference?.typeElement is KtNullableType