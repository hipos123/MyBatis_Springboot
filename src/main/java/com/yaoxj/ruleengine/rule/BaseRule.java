package com.yaoxj.ruleengine.rule;

import com.yaoxj.ruleengine.dto.RuleDto;

public interface BaseRule {
    boolean execute(RuleDto dto);
}
