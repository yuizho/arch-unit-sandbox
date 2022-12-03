package com.github.yuizho;

import com.github.yuizho.entity.Sample;
import com.github.yuizho.repository.SampleRepository;
import com.tngtech.archunit.core.domain.JavaAccess;
import com.tngtech.archunit.core.importer.ClassFileImporter;

import java.util.Set;

public class Main {
    public static void main(String... args) {
        var javaClasses = new ClassFileImporter().importPackages("com.github.yuizho");

        // getMethodで特定のメソッドに絞ることもできる
        // https://www.javadoc.io/doc/com.tngtech.archunit/archunit/0.17.0/com/tngtech/archunit/core/domain/JavaClass.html#getMethod(java.lang.String,java.lang.Class...)
        var saveMethod = javaClasses.get(SampleRepository.class)
                .getMethod("save", Sample.class);

        // 深すぎる場合はStackOverflowするかもだけど、一応呼び出し元を再帰的にたどっていくことも可能
        // getAccessesToSelfで、その対象にアクセスしてるものを取得できる
        // https://qiita.com/opengl-8080/items/0d0006918c2936c9986e#%E5%8F%82%E7%85%A7%E5%85%83%E3%81%AE%E5%8F%96%E5%BE%97
        findUsages(saveMethod.getAccessesToSelf());
    }

    public static void findUsages(Set<? extends JavaAccess<?>> javaAccesses) {
        for (var javaAccess : javaAccesses) {
            // annotationも取れる
            javaAccess
                    .getOwner()
                    .getAnnotations()
                    .forEach(javaAnnotation -> System.out.println("    " + javaAnnotation.getDescription()));
            System.out.println(javaAccess.getDescription());
            findUsages(javaAccess.getOwner().getAccessesToSelf());
        }
    }
}
