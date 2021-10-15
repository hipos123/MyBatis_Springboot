package com.yaoxj.ruleengine.rule;

import com.yaoxj.ruleengine.dto.RuleDto;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-09-01 15:10
 **/
// 规则模板
public abstract class AbstractRule implements BaseRule {

    protected <T> T convert(RuleDto dto) {
        return (T) dto;
    }

    @Override
    public boolean execute(RuleDto dto) {
        return executeRule(convert(dto));
    }

    protected <T> boolean executeRule(T t) {
        return true;
    }
}
