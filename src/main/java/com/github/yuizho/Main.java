package com.github.yuizho;

import com.github.yuizho.annotation.Transactional;
import com.github.yuizho.repository.BaseRepository;
import com.tngtech.archunit.core.domain.JavaAccess;
import com.tngtech.archunit.core.domain.JavaCodeUnit;
import com.tngtech.archunit.core.importer.ClassFileImporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String... args) {
        var javaClasses = new ClassFileImporter().importPackages("com.github.yuizho");

        // オーバーライドしていなければ、基底クラスのメソッドそのまま調べればよさそう
        var saveMethod = javaClasses.get(BaseRepository.class).getMethod("save", Object.class);

        // 深すぎる場合はStackOverflowするかもだけど、一応呼び出し元を再帰的にたどっていくことも可能
        // getAccessesToSelfで、その対象にアクセスしてるものを取得できる
        // https://qiita.com/opengl-8080/items/0d0006918c2936c9986e#%E5%8F%82%E7%85%A7%E5%85%83%E3%81%AE%E5%8F%96%E5%BE%97
        var tracer = new Tracer();
        tracer.findUsages(saveMethod.getAccessesToSelf(), List.of(saveMethod));

        tracer.getViolations().forEach(System.out::println);
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

        public List<Violation> getViolations() {
            return this.violations;
        }
    }

    public record Violation(List<JavaCodeUnit> callStack) {
        @Override
        public String toString() {
            return callStack
                    .stream()
                    .map(j -> j.getFullName() + " " + j.getSourceCodeLocation())
                    .collect(Collectors.joining(", "));
        }
    }
}

