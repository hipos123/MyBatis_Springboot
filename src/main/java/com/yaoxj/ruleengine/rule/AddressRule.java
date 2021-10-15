package com.yaoxj.ruleengine.rule;

import com.yaoxj.ruleengine.dto.RuleConstant;
import com.yaoxj.ruleengine.dto.RuleDto;
import com.yaoxj.ruleengine.rule.AbstractRule;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-09-01 15:14
 **/
public class AddressRule extends AbstractRule {

    @Override
    public boolean execute(RuleDto dto) {
        System.out.println("AddressRule invoke!");
        if (dto.getAddress().startsWith(RuleConstant.MATCH_ADDRESS_START)) {
            return true;
        }
        return false;
    }
}