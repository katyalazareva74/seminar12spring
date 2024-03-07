## Домашнее задание
1. В проект Notes добавлен spring Integration. Все успешные запросы пользователя записываются в файл note.txt, 
который находится в папке note. При перезапуске приложения запросы пользователя будут добавляться в файл note.txt.
2. В проект добавлен паттерн Observer. Есть лист заявок (notes), когда добавляется в список новая заявка, 
то формируется уведомление об этом и выводится в консоль. В Spring этот паттерн реализован через механизм
событий и слушателей. Событие (NoteCreateEvent)  и слушатель (NoteCreateListener)  находятся в папке service.
Публикация производится в NoteController в методе addNote.