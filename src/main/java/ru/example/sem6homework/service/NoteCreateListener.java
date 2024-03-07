package ru.example.sem6homework.service;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.example.sem6homework.model.Note;

/**
 * Создание слушателя NoteCreateListener, создание уведомления о том, что создана новая заявка
 */
@Component
public class NoteCreateListener implements ApplicationListener<NoteCreateEvent> {
    @Override
    public void onApplicationEvent(NoteCreateEvent event) {
        Note note = event.getNote();
        System.out.println("Заявка создана: " + note.getTitle()+"  Дата и время создания:"+note.getLocalDateTime());
    }
}
