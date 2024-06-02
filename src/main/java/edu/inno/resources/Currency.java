package edu.inno.resources;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Currency {
    RUB("Рубль"),
    USD("Доллар"),
    EUR("Евро");

    private String name;
}
