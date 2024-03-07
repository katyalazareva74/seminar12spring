package ru.example.sem6homework.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.sem6homework.exception.ResourceNotFoundException;
import ru.example.sem6homework.model.Note;
import ru.example.sem6homework.service.FileGateWay;
import ru.example.sem6homework.service.NoteCreateEvent;
import ru.example.sem6homework.service.NoteService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/notes")
public class NoteController {
    private final NoteService service;
    private final FileGateWay fileGateWay;
    private ApplicationEventPublisher publisher;

    /**
     * Запрос возвращает все записи
     *
     * @return - возвращает список записей
     */
    @GetMapping
    public List<Note> findAllNotes() {
        fileGateWay.writeToFile("note.txt", "Запрос всего списка заявок");
        return service.findAll();
    }

    /**
     * Запрос создает запись
     * @param note - сама запись
     * @return - возращает сделанную запись
     */
    /**
     * Публикация события
     */
    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody Note note) {
        ResponseEntity<Note> note1 = new ResponseEntity<>(service.createNote(note), HttpStatus.CREATED);
        fileGateWay.writeToFile("note.txt", "Создалась новая заявка: " + note.getTitle());
        publisher.publishEvent(new NoteCreateEvent(this, note1.getBody()));
        return note1;
    }

    /**
     * Запрос на поиск записи по id
     *
     * @param id - номер записи
     * @return - возвращает найденную запись по номеру, а если запись не найдена, то
     * выбрасывается исключение ResourceNotFoundException
     * @throws ResourceNotFoundException - это исключение выдает код ошибки 404 (ресурс не найден) на стороне клиента
     */
    @GetMapping("{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        ResponseEntity<Note> note1 = ResponseEntity.ok(service.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note not found with id " + id)));
        fileGateWay.writeToFile("note.txt", "Найти заявку по Id: " + id);
        return ResponseEntity.ok(note1.getBody());
    }

    /**
     * Запрос на редактирование записи по номеру (id)
     *
     * @param id   - номер записи
     * @param note - новая запись
     * @return - возвращает отредактированную запись, а если запись не найдена, то
     * выбрасывается исключение ResourceNotFoundException
     * @throws ResourceNotFoundException - это исключение выдает иод ошибку 404 (ресурс не найден) на стороне клиента
     */
    @PutMapping("{id}")
    public ResponseEntity<Note> updateNote(@PathVariable("id") Long id, @RequestBody Note note) throws ResourceNotFoundException {
        note.setId(id);
        ResponseEntity<Note> note1 = ResponseEntity.ok(service.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note not found with id " + id)));
        service.createNote(note);
        fileGateWay.writeToFile("note.txt", "Обновление заявки по Id: " + id);
        return ResponseEntity.ok(note);
    }

    /**
     * Запрос на удаление записи по id
     *
     * @param id - номер записи
     * @return - если запись найдена, то возвращается код 200, а если запись не найдена, то
     * выбрасывается исключение ResourceNotFoundException
     * @throws ResourceNotFoundException - это исключение выдает код ошибки 404 (ресурс не найден) на стороне клиента
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        ResponseEntity<Note> note = ResponseEntity.ok(service.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note not found with id " + id)));
        service.deleteNote(id);
        fileGateWay.writeToFile("note.txt", "Удаление заявки по Id: " + id);
        return ResponseEntity.ok().build();
    }
}
