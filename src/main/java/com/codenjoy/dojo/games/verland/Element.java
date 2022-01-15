package com.codenjoy.dojo.games.verland;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.services.printer.CharElement;
import com.codenjoy.dojo.services.printer.TeamElement;

public enum Element implements CharElement, TeamElement {

/// Мой герой

    HERO_DEAD('X', -1,          "Герой заразился инфекцией."),

    HERO('♥', -2,               "Герой."),

    HERO_CURE('!', -3,          "Попытка героем зачистить инфекцию. " +
                                "Если инфекция была устранена ситуация вокруг обновится. " +
                                "Если герой ошибся и зона была не инфицирована - штраф."),

    HERO_HEALING('x', -4,       "На секунду после окончания игры поле открывается " +
                                "и можно увидеть какую инфекцию удалось обеззаразить герою."),

/// Другой герой из моей команды

    OTHER_HERO_DEAD('Y', -5,     "Герой из моей команды заразился инфекцией."),

    OTHER_HERO('♠', -6,          "Герой из моей команды в работе."),

    OTHER_HERO_CURE('+', -7,     "Попытка героем из моей команды зачистить инфекцию. " +
                                 "Если инфекция была устранена ситуация вокруг обновится. " +
                                 " Если герой ошибся и зона была не инфицирована - штраф."),

    OTHER_HERO_HEALING('y', -8,  "На секунду после окончания игры поле открывается " +
                                 "и можно увидеть какую инфекцию удалось обеззаразить " +
                                 "герою из моей команды."),

/// Вражеский герой

    ENEMY_HERO_DEAD('Z', -9,     "Вражеский герой заразился инфекцией."),

    ENEMY_HERO('♣', -10,         "Вражеский герой в работе."),

    ENEMY_HERO_HEALING('z', -11, "На секунду после окончания игры поле открывается " +
                                 "и можно увидеть какую инфекцию удалось обеззаразить " +
                                 "вражескому герою."),

/// Разное на поле

    INFECTION('o', -12,          "На секунду после смерти героя поле открывается " +
                                 "и можно увидеть где была инфекция."),

    HIDDEN('*', -13,             "Туман - место где еще не бывал герой. Возможно эта зона инфицирована."),

    PATHLESS('☼', -14,           "Непроходимые территории - обычно граница поля, но может быть и " +
                                 "простое на пути героя."),

/// Маркеры заражений вокруг

    CLEAR(' ', 0,                "Вокруг этой зоны нет заражений."),

    CONTAGION_ONE('1', 1,        "Вокруг этой зоны было зафиксировано одно заражение."),

    CONTAGION_TWO('2', 2,       "Вокруг этой зоны было зафиксировано два заражения."),

    CONTAGION_THREE('3', 3,     "Вокруг этой зоны было зафиксировано три заражения."),

    CONTAGION_FOUR('4', 4,      "Вокруг этой зоны было зафиксировано четыре заражения."),

    CONTAGION_FIVE('5', 5,      "Вокруг этой зоны было зафиксировано пять заражений."),

    CONTAGION_SIX('6', 6,       "Вокруг этой зоны было зафиксировано шесть заражений."),

    CONTAGION_SEVEN('7', 7,     "Вокруг этой зоны было зафиксировано семь заражений."),

    CONTAGION_EIGHT('8', 8,     "Вокруг этой зоны было зафиксировано восемь заражений.");

    private final char ch;
    private final int value;
    private final String info;

    Element(char ch, int value, String info) {
        this.ch = ch;
        this.value = value;
        this.info = info;
    }

    @Override
    public char ch() {
        return ch;
    }

    @Override
    public String info() {
        return info;
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(ch);
    }

    public static Element valueOf(int value) {
        for (Element element : values()) {
            if (element.value == value) {
                return element;
            }
        }
        throw new IllegalArgumentException("Value not found for: " + value);
    }

    public boolean isHero() {
        return  is(heroes);
    }

    public boolean isOtherHero() {
        return  is(otherHeroes);
    }

    public boolean isEnemyHeroes() {
        return  is(enemyHeroes);
    }

    public boolean isPathless() {
        return  is(pathless);
    }

    public boolean isInfections() {
        return  is(infections);
    }

    public boolean isHidden() {
        return  is(hidden);
    }

    public boolean isContagions() {
        return  is(contagions);
    }

    public boolean isHealing() {
        return  is(healing);
    }

    public boolean isCure() {
        return  is(cure);
    }

    public static final Element[] heroes = new Element[]{
            HERO_DEAD,
            HERO,
    };

    public static final Element[] otherHeroes = new Element[]{
            OTHER_HERO_DEAD,
            OTHER_HERO,
    };

    public static final Element[] enemyHeroes = new Element[]{
            ENEMY_HERO_DEAD,
            ENEMY_HERO,
    };

    public static final Element[] pathless = new Element[]{
            PATHLESS,
    };

    public static final Element[] infections = new Element[]{
            INFECTION,
    };

    public static final Element[] hidden = new Element[]{
            HIDDEN,
    };

    public static final Element[] clear = new Element[]{
            CLEAR,
    };

    public static final Element[] contagions = new Element[]{
            CONTAGION_ONE,
            CONTAGION_TWO,
            CONTAGION_THREE,
            CONTAGION_FOUR,
            CONTAGION_FIVE,
            CONTAGION_SIX,
            CONTAGION_SEVEN,
            CONTAGION_EIGHT,
    };

    public static final Element[] healing = new Element[]{
            HERO_HEALING,
            OTHER_HERO_HEALING,
            ENEMY_HERO_HEALING,
    };

    public static final Element[] cure = new Element[]{
            HERO_CURE,
            OTHER_HERO_CURE,
    };

    @Override
    public Element otherHero() {
        switch (this) {
            case HERO:      return OTHER_HERO;
            case HERO_DEAD: return OTHER_HERO_DEAD;
        }
        throw new IllegalArgumentException("Bad hero state: " + this);
    }

    @Override
    public Element enemyHero() {
        switch (this) {
            case HERO:      return ENEMY_HERO;
            case HERO_DEAD: return ENEMY_HERO_DEAD;
        }
        throw new IllegalArgumentException("Bad hero state: " + this);
    }
}