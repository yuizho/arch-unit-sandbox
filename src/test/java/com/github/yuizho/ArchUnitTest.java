package com.github.yuizho;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.github.yuizho")
public class ArchUnitTest {
    @ArchTest
    public static final ArchRule myRule = classes()
            .that().resideInAnyPackage("..service..")
            .should().onlyBeAccessed().byAnyPackage("..controller");
}
