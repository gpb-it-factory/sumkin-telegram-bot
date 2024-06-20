package com.gpb.sumkintelegrambot.configuration;

public enum Command {
    START("/start", "стартовое сообщение"),
    HELP("/help", "список доступных команд"),
    REGISTER("/register", "регистрация нового пользователя"),
    PING("/ping", "пришлет в ответ pong"),
    REGACCOUNT("/regaccount", "регистрация счета, формат: /regaccount название счета"),
    TRANSFER("/transfer", "перевод средств, формат: /transfer получатель сумма");

    public final String name;
    public final String description;

    Command(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
