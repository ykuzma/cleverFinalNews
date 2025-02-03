RESTful платформа, реализующая функционал для работы с новостями.

**Описание api в файле docApi.html в корне проекта**


Приложение состоит из двух микросервисов:
 - news - https://github.com/ykuzma/cleverFinalNews;
 - comments - https://github.com/ykuzma/cleverFinalComments;



Для работы приложения необходимо скачать в локальный репозиторий
стартер для логирования - https://github.com/ykuzma/loggingStarter:
 - @Logging - маркерная аннотация для логгирования методов контроллера;
 - Property - logging.controller.enable = true | false(default).

стартер для обработки ошибок - https://github.com/ykuzma/excaptionHandlerStarter.git;
 - @ExceptionStatus(value = (Enum)HttpStatus) - аннотация для кастомных классов ошибок;
 - Property - exception.handle.enable = true | false(default).


Запуск приложения из контейнера:
 - docker create network news;
 - docker compose up


В приложении 2 вида кэша:
 - prod - Redis;
 - default - кастомный кэш.

Кастомный кэш - Property:
 - application.cache.strategy = lfu | lru
 - application.cache.limit = int (размер кэша)

