# Назначение репозитория
Репозиторий для тестовых заданий

#Сборка проекта
Сначала собираем проект: 

```shell script
mvn package
```
В папке `target` появится `.jar` файл и папка `lib` со всеми зависимостями.

`.jar` файл запускаем командой:
```shell script
java -jar application-task-1.0-SNAPSHOT.jar "{путь к входным данным}"
```