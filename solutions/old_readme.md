Пример кода.
Тесты запускать на эмуляторе. Например, отчет с телефона Samsung не получить.

Для запуска анализа покрытия кода тестами, выполнить команды:
.\gradlew clean createDebugCoverageReport jacocoTestReport

Для запуска анализа покрытия кода Unit тестами
.\gradlew clean jacocoUnitTestReport

После чего открыть файл TemplateProject2\app\build\reports\jacoco\testDebugUnitTestCoverage\html
\index.html
-------------------------------------------------------------------------------------
Sonar
Выполнить D:\sonar-scanner\bin\sonar-scanner.bat находясь в проекте
Открыть http://localhost:9000

Классы Util - это классы доступ к которым есть у View

В идеале через dagger, настроить, что бы сервисы не могли общаться.
ResourceUtil создается для всех отдельно (ошибка)