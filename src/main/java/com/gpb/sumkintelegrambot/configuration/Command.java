package com.gpb.sumkintelegrambot.configuration;

public enum Command {
    START("/start", "стартовое сообщение"),
    HELP("/help", "список доступных команд"),
    REGISTER("/register", "регистрация нового пользователя"),
    PING("/ping", "пришлет в ответ pong");

    public final String name;
    public final String description;

    Command(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
