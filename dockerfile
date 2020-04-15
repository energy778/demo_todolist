#файл нужен для упаковки приложения в docker-контейнер

#берем самый лайтовый линукс с java-машиной 8 (open jdk)
FROM openjdk:8-jdk-alpine

ARG JAR_FILE

#создаем директорию
RUN mkdir -p /apps
#и копируем туда наш jar-файл
COPY ./target/${JAR_FILE} /apps/app.jar
#также копируем скрипт настройки
COPY ./entrypoint.sh /apps/entrypoint.sh
#даем файлу права на запуск
RUN chmod +x /apps/entrypoint.sh

#и запускаем его на стороне приемника
CMD ["/apps/entrypoint.sh"]
