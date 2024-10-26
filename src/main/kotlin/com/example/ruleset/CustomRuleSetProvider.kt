@file:Suppress("ktlint:standard:filename")

package com.example.ruleset

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId

class ComposeRuleSetProvider : RuleSetProviderV3(id = RuleSetId("compose")) {
    override fun getRuleProviders() =
        setOf(
            RuleProvider { EnforceIconTextButtonRule() },
            RuleProvider { EnforceIconTextButtonRule22() },
        )
}