package com.github.yuizho;

import com.github.yuizho.annotation.Transactional;
import com.github.yuizho.entity.Sample;
import com.github.yuizho.repository.SampleRepository;
import com.tngtech.archunit.core.domain.JavaAccess;
import com.tngtech.archunit.core.domain.JavaCodeUnit;
import com.tngtech.archunit.core.importer.ClassFileImporter;

import java.util.ArrayList;
import java.util.List;
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
        var tracer = new Tracer();
        tracer.findUsages(saveMethod.getAccessesToSelf(), List.of(saveMethod));

        tracer.violations.forEach(System.out::println);
    }

    public static class Tracer {
        private final List<Violation> violations;

        public Tracer() {
            this.violations = new ArrayList<>();
        }

        public void findUsages(Set<? extends JavaAccess<?>> javaAccesses, List<JavaCodeUnit> callStack) {
            // トランザクション境界が作られていないコールスタックをチェック (境界が作られてたらcontinue)
            for (JavaAccess<?> javaAccess : javaAccesses) {
                var appliedTransactional = javaAccess
                        .getOwner()
                        .tryGetAnnotationOfType(Transactional.class)
                        .isPresent();
                if (appliedTransactional) {
                    continue;
                }

                var copiedCallStack = new ArrayList<>(callStack);
                copiedCallStack.add(javaAccess.getOwner());

                var callers = javaAccess.getOwner().getAccessesToSelf();
                if (callers.isEmpty()) {
                    violations.add(new Violation(copiedCallStack));
                    continue;
                }

                findUsages(callers, copiedCallStack);
            }
        }
    }

    public static class Violation {
        private final List<JavaCodeUnit> callStack;

        public Violation(List<JavaCodeUnit> callStack) {
            this.callStack = callStack;
        }

        @Override
        public String toString() {
            return "Violation{" +
                    "callStack=" + callStack +
                    '}';
        }
    }
}

