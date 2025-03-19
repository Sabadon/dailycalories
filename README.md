# Daily Calories Tracking App

[![Java](https://img.shields.io/badge/Java-21%2B-blue)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.3-green)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17.2-blue)](https://www.postgresql.org/)
[![Swagger](https://img.shields.io/badge/Swagger-2.8.5-green)](https://swagger.io/)

Приложение для учёта калорий и анализа питания пользователей.

***

## Основные возможности
- CRUD операции над пользователями
- Добавление блюд с указанием их калорийности
- Учёт приёмов пищи (состав, время, порции)
- Отчёты:
    - Отчет за день с суммой всех калорий и приемов пищи
    - Проверка, уложился ли пользователь в свою дневную норму калорий
    - История питания по дням

***

## Технологии
- **Backend**: Java 21, Spring Boot 3.4.3
- **База данных**: PostgreSQL 17.2
- **ORM**: Spring Data JPA
- **Документация API**: Swagger
- **Сборка**: Gradle
- **Контейнеризация**: Docker Compose

***

## Запуск проекта

### Предварительные требования
- Установленные [Docker](https://www.docker.com/) и [Java 21+](https://adoptium.net/)


### Шаги:
1. **Запуск PostgreSQL**:
   ```bash
   docker-compose -f compose.yaml up -d
   ```
2. **Сборка и запуск приложения:**
   ```bash
   ./gradlew bootRun
   ```
***

## Документация
В проекте присутсвует Swagger документация API

http://localhost:8080/swagger-ui/index.html

***

## Тестирование API
Для тестировании API можно использовать коллекции bruno-collection для Bruno и postman-collection для Postman

***

## TODO
- [ ] Добавить авторизацию и работу с Spring Security
- [ ] Реализовать экспорт отчётов в PDF

***
## Контакты
По вопросам сотрудничества или предложений:

[sabadondanil@gmail.com]()