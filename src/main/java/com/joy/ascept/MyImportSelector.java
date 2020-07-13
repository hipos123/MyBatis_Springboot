package com.joy.ascept;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description:
 * @author: yaoxj
 * @create: 2020-07-13 17:24
 **/
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.joy.ascept.ImportSelectorInstant"};
    }
}
