package ru.example.sem6homework.service;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.example.sem6homework.model.Note;


/**
 * Создание события NoteCreateEvent
 */
@Getter
public class NoteCreateEvent extends ApplicationEvent {
    private Note note;

    public NoteCreateEvent(Object source, Note note) {
        super(source);
        this.note = note;
    }
}
