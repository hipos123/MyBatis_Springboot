package com.yaoxj.ruleengine.rule;

import com.yaoxj.ruleengine.dto.RuleConstant;
import com.yaoxj.ruleengine.dto.RuleDto;
import com.yaoxj.ruleengine.rule.AbstractRule;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-09-01 15:39
 **/
public class AgeRule extends AbstractRule {
    @Override
    public boolean execute(RuleDto dto) {
        System.out.println("Age invoke!");
        if (dto.getAge()==RuleConstant.MATCH_AGE_START) {
            return true;
        }
        return false;
    }
}
