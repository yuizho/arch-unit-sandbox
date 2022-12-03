package com.github.yuizho;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.github.yuizho")
public class ArchUnitTest {
    // jsut shakyou code
    // https://www.archunit.org/userguide/html/000_Index.html#_using_junit_4_or_junit_5
    @ArchTest
    public static final ArchRule serviceRule = classes()
            .that().resideInAnyPackage("..service..")
            .should().onlyBeAccessed().byAnyPackage("..controller", "..service");

    @ArchTest
    public static final ArchRule repositoryRule = classes()
            .that().resideInAnyPackage("..repository..")
            .should().onlyBeAccessed().byAnyPackage("..service", "..repository");
}
