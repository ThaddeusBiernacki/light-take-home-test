package org.light.challenge.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class App {

    public static void main (String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {
            // hand off to a non-static bean
            var runner = ctx.getBean(ApplicationRunner.class);
            runner.runApplication(args);
        }
    }
}