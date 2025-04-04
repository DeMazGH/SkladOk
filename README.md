#  "СкладОк" - это небольшое веб-приложение предназначенное для автоматизации учёта текстильной продукции (носков) на складе магазина.

## Краткое описание функционала проекта
### Кладовщики имеют возможность:
- Регистрировать поступление товара на склад.
- Регистрировать выбытие товара со склада.
- Получать актуальную информацию о наличии товара с определенными параметрами.

### API
### 1. Регистрация поступления товара
Запрос: POST /storage/items/incoming
Тело запроса (JSON):

```
{
  "itemColor": "строковое значение",
  "materialPercentage": целое число от 0 до 100,
  "units": целое положительное число
}
```

Коды ответа:    
200 - Операция выполнена успешно.    
400 - Некорректные параметры запроса.    
500 - Внутренняя ошибка сервера.    


### 2. Регистрация выбытия товара
Запрос: POST /storage/items/outgoing
Тело запроса (JSON):

```
{
  "itemColor": "строковое значение",
  "materialPercentage": целое число от 0 до 100,
  "units": целое положительное число
}
```

Коды ответа:    
200 - Операция выполнена успешно.    
400 - Некорректные параметры запроса.    
500 - Внутренняя ошибка сервера.  

### 3. Получение информации о товаре
Запрос: GET /storage/items

Параметры запроса:
- itemColor - строковое значение цвета.
- compareType - тип сравнения для процентного содержания материала (допустимые значения: "gt", "lt", "eq").
- materialPercentage - числовое значение для сравнения.

Примеры запросов:  
/storage/items?itemColor=crimson&compareType=gt&materialPercentage=85  
/storage/items?itemColor=navy&compareType=lt&materialPercentage=15  

Результат: Общее количество единиц товара, соответствующего заданным критериям (числовое значение).

## Сборка и запуск проекта

### Сборка
Предварительно необходимо настроить данные с доcтупом к PostgreSQL в файле {корневая директория проекта}/src/main/resources/application.properties  

Сборка производится системой gradle 8.13 версии или выше.  
Для сборки в корневой директории проекта нужно запустить команду `gradlew build` (Linux) или `./gradlew build` (Windows).  
Собранный jar-файл находится {корневая директория проекта}/build/libs/{название проекта}.jar  

### Запуск
Для запуска требуется Java 17 версии или выше.
В директории с собранным проектом нужно запустить команду `java -jar SkladOk.jar`

## Технологии в проекте
Язык и окружение - Java 17, Spring Boot, Spring Web, Spring Data JPA, Hibernate, PostgreSQL, Liquibase.

## Автор:
Маза Денис
