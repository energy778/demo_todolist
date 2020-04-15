#!/usr/bin/env sh

#чтобы приложение запустилось необходимо выполнить еще одну команду: "container:release"
heroku container:release web app=todolist
