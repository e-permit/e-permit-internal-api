package epermit.data.tasks;

import java.util.concurrent.Executor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import epermit.common.MessageCreatedEvent;
import lombok.extern.slf4j.Slf4j;

@EnableAsync(proxyTargetClass = true)
@Component
@Slf4j
public class MessageEventListener implements AsyncConfigurer {

    @Async
    @EventListener
    public void onMessageCreatedEvent(MessageCreatedEvent event) {
        log.debug("received ticket updated event");
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(100);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
