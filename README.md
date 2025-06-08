# СТО-сервис: управление заявками

Spring Boot приложение для автоматизации 
заявок в автосервисе. 
Поддерживает:
регистрацию 
изменение статусов с сохранением истории 
которое проходит через Kafka

## Стек технологий

- Java 21
- Spring Boot 3.4.2
- Spring Data JPA + H2
- Spring Kafka
- Lombok
- Docker Compose (Kafka, Zookeeper, аппка)

## Быстрый старт
### Запусти через Docker после сборки проекта

```bash
docker compose up --build
```

Приложение будет доступно на [http://localhost:8080](http://localhost:8080)

### Создание заявки
`POST /api/v1/requests`
```json
{
  "clientName": "Test",
  "carModel": "Toyota Camry 70",
  "description": "very vip"
}
```

##  Изменение статуса(идет через кафку)

- Топик: `statusChange`
- Отправка события через ендпоинт `POST /api/v1/requests/status`
- Структура json`a:

```json
{
  "id": 1,
  "newStatus": "REPAIRING",
  "changedBy": "admin",
  "reason": "client confirmed scope"
}
```

## БД

H2-консоль: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

- JDBC URL: `jdbc:h2:mem:cto`
- User: `sa`
- Password: (пустой)


## Логика

- При изменении статуса:
    - Заявка обновляется
    - Добавляется запись в `status_history`
    - Если статус `COMPLETED`, логируется МОК смс уведомление клиенту
- Все операции происходят в транзакции (`@Transactional`)