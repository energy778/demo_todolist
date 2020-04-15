#!/usr/bin/env sh

#создаем приложение с наименованием todolist
heroku apps:create todolist
#добавляем в приложение, которое называется todolist БД postrgresql
heroku addons:create heroku-postrgresql:hobby-dev --app todolist
