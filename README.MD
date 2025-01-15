RESTful платформа, реализующая функционал для работы с новостями.

Приложение состоит из двух микросервисов:
 - news - https://github.com/ykuzma/cleverFinalNews;
 - comments - https://github.com/ykuzma/cleverFinalComments;

Для работы приложения необходимо скачать в локальный репозиторий
стартер для логирования - https://github.com/ykuzma/loggingStarter;
стартер для обработки ошибок - https://github.com/ykuzma/excaptionHandlerStarter.git;

Запуск приложения из контейнера:
 - docker create network news;
 - docker compose up