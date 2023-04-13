package org.image;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    @Test
    public void testLogger() {
        Logger appLogger = (Logger) LoggerFactory.getLogger(App.class);

        // Создаем ListAppender и добавляем его к логгеру
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        appLogger.addAppender(listAppender);

        // Устанавливаем уровень логгирования
        appLogger.setLevel(Level.INFO);

        // Вызываем метод для логгирования
        App.logSelectedImage("test-image.png");

        // Проверяем, что записи лога соответствуют ожидаемым
        assertEquals(1, listAppender.list.size());
        assertEquals(Level.INFO, listAppender.list.get(0).getLevel());
        assertEquals("Selected image: test-image.png", listAppender.list.get(0).getFormattedMessage());
    }
}