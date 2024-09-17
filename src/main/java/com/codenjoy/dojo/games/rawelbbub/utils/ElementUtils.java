package com.codenjoy.dojo.games.rawelbbub.utils;

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

import com.codenjoy.dojo.games.rawelbbub.model.Element;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.printer.TeamElement;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.codenjoy.dojo.games.rawelbbub.model.Element.*;
import static com.codenjoy.dojo.services.Direction.*;

public class ElementUtils {

    public final static Map<Element, Map<Direction, Element>> destroyIceberg = new LinkedHashMap<>();
    public final static Map<Element, Element> growIceberg = new LinkedHashMap<>();
    public final static Map<Element, Integer> power = new LinkedHashMap<>();

    static {
        transformGrow(ICEBERG_HUGE,              ICEBERG_HUGE);

        transformGrow(ICEBERG_MEDIUM_LEFT,       ICEBERG_HUGE);
        transformGrow(ICEBERG_MEDIUM_RIGHT,      ICEBERG_HUGE);
        transformGrow(ICEBERG_MEDIUM_UP,         ICEBERG_HUGE);
        transformGrow(ICEBERG_MEDIUM_DOWN,       ICEBERG_HUGE);

        transformGrow(ICEBERG_SMALL_LEFT_LEFT,   ICEBERG_MEDIUM_LEFT);
        transformGrow(ICEBERG_SMALL_RIGHT_RIGHT, ICEBERG_MEDIUM_RIGHT);
        transformGrow(ICEBERG_SMALL_UP_UP,       ICEBERG_MEDIUM_RIGHT);
        transformGrow(ICEBERG_SMALL_DOWN_DOWN,   ICEBERG_MEDIUM_DOWN);

        transformGrow(ICEBERG_SMALL_LEFT_RIGHT,  ICEBERG_MEDIUM_RIGHT);
        transformGrow(ICEBERG_SMALL_UP_DOWN,     ICEBERG_MEDIUM_UP);

        transformGrow(ICEBERG_SMALL_UP_LEFT,     ICEBERG_MEDIUM_LEFT);
        transformGrow(ICEBERG_SMALL_UP_RIGHT,    ICEBERG_MEDIUM_RIGHT);
        transformGrow(ICEBERG_SMALL_DOWN_LEFT,   ICEBERG_MEDIUM_DOWN);
        transformGrow(ICEBERG_SMALL_DOWN_RIGHT,  ICEBERG_MEDIUM_RIGHT);

        transformGrow(WATER,                     ICEBERG_SMALL_LEFT_RIGHT);

        transformDestroy(ICEBERG_HUGE,         LEFT,  ICEBERG_MEDIUM_LEFT);
        transformDestroy(ICEBERG_HUGE,         RIGHT, ICEBERG_MEDIUM_RIGHT);
        transformDestroy(ICEBERG_HUGE,         UP,    ICEBERG_MEDIUM_UP);
        transformDestroy(ICEBERG_HUGE,         DOWN,  ICEBERG_MEDIUM_DOWN);

        transformDestroy(ICEBERG_MEDIUM_LEFT,  LEFT,  ICEBERG_SMALL_LEFT_LEFT);
        transformDestroy(ICEBERG_MEDIUM_LEFT,  RIGHT, ICEBERG_SMALL_LEFT_RIGHT);
        transformDestroy(ICEBERG_MEDIUM_LEFT,  UP,    ICEBERG_SMALL_UP_LEFT);
        transformDestroy(ICEBERG_MEDIUM_LEFT,  DOWN,  ICEBERG_SMALL_DOWN_LEFT);

        transformDestroy(ICEBERG_MEDIUM_RIGHT, LEFT,  ICEBERG_SMALL_LEFT_RIGHT);
        transformDestroy(ICEBERG_MEDIUM_RIGHT, RIGHT, ICEBERG_SMALL_RIGHT_RIGHT);
        transformDestroy(ICEBERG_MEDIUM_RIGHT, UP,    ICEBERG_SMALL_UP_RIGHT);
        transformDestroy(ICEBERG_MEDIUM_RIGHT, DOWN,  ICEBERG_SMALL_DOWN_RIGHT);

        transformDestroy(ICEBERG_MEDIUM_UP,    LEFT,  ICEBERG_SMALL_UP_LEFT);
        transformDestroy(ICEBERG_MEDIUM_UP,    RIGHT, ICEBERG_SMALL_UP_RIGHT);
        transformDestroy(ICEBERG_MEDIUM_UP,    UP,    ICEBERG_SMALL_UP_UP);
        transformDestroy(ICEBERG_MEDIUM_UP,    DOWN,  ICEBERG_SMALL_UP_DOWN);

        transformDestroy(ICEBERG_MEDIUM_DOWN,  LEFT,  ICEBERG_SMALL_DOWN_LEFT);
        transformDestroy(ICEBERG_MEDIUM_DOWN,  RIGHT, ICEBERG_SMALL_DOWN_RIGHT);
        transformDestroy(ICEBERG_MEDIUM_DOWN,  UP,    ICEBERG_SMALL_UP_DOWN);
        transformDestroy(ICEBERG_MEDIUM_DOWN,  DOWN,  ICEBERG_SMALL_DOWN_DOWN);

        power.put(WATER, 0);
        power.put(ICEBERG_HUGE, 3);

        power.put(ICEBERG_MEDIUM_LEFT, 2);
        power.put(ICEBERG_MEDIUM_RIGHT, 2);
        power.put(ICEBERG_MEDIUM_UP, 2);
        power.put(ICEBERG_MEDIUM_DOWN, 2);

        power.put(ICEBERG_SMALL_LEFT_LEFT, 1);
        power.put(ICEBERG_SMALL_RIGHT_RIGHT, 1);
        power.put(ICEBERG_SMALL_UP_UP, 1);
        power.put(ICEBERG_SMALL_DOWN_DOWN, 1);

        power.put(ICEBERG_SMALL_LEFT_RIGHT, 1);
        power.put(ICEBERG_SMALL_UP_DOWN, 1);

        power.put(ICEBERG_SMALL_UP_LEFT, 1);
        power.put(ICEBERG_SMALL_UP_RIGHT, 1);
        power.put(ICEBERG_SMALL_DOWN_LEFT, 1);
        power.put(ICEBERG_SMALL_DOWN_RIGHT, 1);
    }

    private static void transformGrow(Element from, Element to) {
        growIceberg.put(from, to);
    }

    private static void transformDestroy(Element from, Direction direction, Element to) {
        // TODO do not use map.containsKey just check that map.get() != null
        if (!destroyIceberg.containsKey(from)) {
            destroyIceberg.put(from, new LinkedHashMap<>());
        }
        destroyIceberg.get(from).put(direction, to);
    }

    public static Element destroyFrom(Element element, Direction direction) {
        if (power.get(element) == 1) {
            return Element.WATER;
        }
        return destroyIceberg.get(element).get(direction);
    }

    public static Element grow(Element element) {
        return growIceberg.get(element);
    }

    public static final Element[] heroes = new Element[]{
            HERO_LEFT,
            HERO_RIGHT,
            HERO_UP,
            HERO_DOWN,
    };

    public static final Element[] barriers = new Element[]{
            REEFS,
            FISHNET,

            ICEBERG_HUGE,

            ICEBERG_MEDIUM_LEFT,
            ICEBERG_MEDIUM_RIGHT,
            ICEBERG_MEDIUM_UP,
            ICEBERG_MEDIUM_DOWN,

            ICEBERG_SMALL_LEFT_LEFT,
            ICEBERG_SMALL_RIGHT_RIGHT,
            ICEBERG_SMALL_UP_UP,
            ICEBERG_SMALL_DOWN_DOWN,

            ICEBERG_SMALL_LEFT_RIGHT,
            ICEBERG_SMALL_UP_DOWN,

            ICEBERG_SMALL_UP_LEFT,
            ICEBERG_SMALL_UP_RIGHT,
            ICEBERG_SMALL_DOWN_LEFT,
            ICEBERG_SMALL_DOWN_RIGHT,
    };

    public static final Element[] otherHeroes = new Element[]{
            OTHER_HERO_LEFT,
            OTHER_HERO_RIGHT,
            OTHER_HERO_UP,
            OTHER_HERO_DOWN,
    };

    public static final Element[] ais = new Element[]{
            AI_PRIZE_LEFT,
            AI_PRIZE_RIGHT,
            AI_PRIZE_UP,
            AI_PRIZE_DOWN,

            AI_LEFT,
            AI_RIGHT,
            AI_UP,
            AI_DOWN,
    };

    public static final Element[] enemies = new Element[]{
            AI_PRIZE_LEFT,
            AI_PRIZE_RIGHT,
            AI_PRIZE_UP,
            AI_PRIZE_DOWN,

            AI_LEFT,
            AI_RIGHT,
            AI_UP,
            AI_DOWN,

            OTHER_HERO_LEFT,
            OTHER_HERO_RIGHT,
            OTHER_HERO_UP,
            OTHER_HERO_DOWN,
    };

    public static final Element[] torpedoes = new Element[]{
            TORPEDO_LEFT,
            TORPEDO_RIGHT,
            TORPEDO_UP,
            TORPEDO_DOWN,
    };

    public static final Element[] fishnet = new Element[]{
            FISHNET,
    };

    public static final Element[] oil = new Element[]{
            OIL,
    };

    public static final Element[] icebergs = new Element[]{
            ICEBERG_HUGE,

            ICEBERG_MEDIUM_LEFT,
            ICEBERG_MEDIUM_RIGHT,
            ICEBERG_MEDIUM_UP,
            ICEBERG_MEDIUM_DOWN,

            ICEBERG_SMALL_LEFT_LEFT,
            ICEBERG_SMALL_RIGHT_RIGHT,
            ICEBERG_SMALL_UP_UP,
            ICEBERG_SMALL_DOWN_DOWN,

            ICEBERG_SMALL_LEFT_RIGHT,
            ICEBERG_SMALL_UP_DOWN,

            ICEBERG_SMALL_UP_LEFT,
            ICEBERG_SMALL_UP_RIGHT,
            ICEBERG_SMALL_DOWN_LEFT,
            ICEBERG_SMALL_DOWN_RIGHT,
    };


    public static final Element[] seaweed = new Element[]{
            SEAWEED,
    };

    public static final Element[] prizes = new Element[]{
            PRIZE,

            PRIZE_IMMORTALITY,
            PRIZE_BREAKING_BAD,
            PRIZE_WALKING_ON_FISHNET,
            PRIZE_VISIBILITY,
            PRIZE_NO_SLIDING,
    };

    public static Element ai(Direction direction, boolean sideView) {
        if (sideView) {
            switch (direction) {
                case LEFT:  return Element.AI_SIDE_LEFT;
                case RIGHT: return Element.AI_SIDE_RIGHT;
                default:    throw new RuntimeException("Wrong AI direction");
            }
        }

        switch (direction) {
            case LEFT:  return Element.AI_LEFT;
            case RIGHT: return Element.AI_RIGHT;
            case UP:    return Element.AI_UP;
            case DOWN:  return Element.AI_DOWN;
            default:    throw new RuntimeException("Wrong AI direction");
        }
    }

    public static Element aiPrize(Direction direction, boolean sideView) {
        if (sideView) {
            switch (direction) {
                case LEFT:  return Element.AI_PRIZE_SIDE_LEFT;
                case RIGHT: return Element.AI_PRIZE_SIDE_RIGHT;
                default:    throw new RuntimeException("Wrong AI prize direction");
            }
        }

        switch (direction) {
            case LEFT:  return Element.AI_PRIZE_LEFT;
            case RIGHT: return Element.AI_PRIZE_RIGHT;
            case UP:    return Element.AI_PRIZE_UP;
            case DOWN:  return Element.AI_PRIZE_DOWN;
            default:    throw new RuntimeException("Wrong AI prize direction");
        }
    }

    public static Element hero(Direction direction, boolean sideView) {
        if (sideView) {
            switch (direction) {
                case LEFT:  return Element.HERO_SIDE_LEFT;
                case RIGHT: return Element.HERO_SIDE_RIGHT;
                default:    throw new RuntimeException("Wrong hero direction");
            }
        }

        switch (direction) {
            case LEFT:  return HERO_LEFT;
            case RIGHT: return HERO_RIGHT;
            case UP:    return Element.HERO_UP;
            case DOWN:  return Element.HERO_DOWN;
            default:    throw new RuntimeException("Wrong hero direction");
        }
    }

    public static Element torpedo(Direction direction, boolean sideView) {
        if (sideView) {
            switch (direction) {
                case LEFT:  return Element.TORPEDO_SIDE_LEFT;
                case RIGHT: return Element.TORPEDO_SIDE_RIGHT;
                default:    throw new RuntimeException("Wrong torpedo direction");
            }
        }

        switch (direction) {
            case LEFT:  return Element.TORPEDO_LEFT;
            case RIGHT: return Element.TORPEDO_RIGHT;
            case UP:    return Element.TORPEDO_UP;
            case DOWN:  return Element.TORPEDO_DOWN;
            default:    throw new RuntimeException("Wrong torpedo direction");
        }
    }

    public static final TeamElement<Element> TEAM_ELEMENT = new TeamElement<>() {
        @Override
        public Element otherHero(Element element) {
            switch (element) {
                case EXPLOSION:       return EXPLOSION;
                case HERO_LEFT:       return OTHER_HERO_LEFT;
                case HERO_RIGHT:      return OTHER_HERO_RIGHT;
                case HERO_UP:         return OTHER_HERO_UP;
                case HERO_DOWN:       return OTHER_HERO_DOWN;
                case HERO_SIDE_LEFT:  return OTHER_HERO_SIDE_LEFT;
                case HERO_SIDE_RIGHT: return OTHER_HERO_SIDE_RIGHT;
            }
            throw new IllegalArgumentException("Bad hero state: " + element);
        }

        @Override
        public Element enemyHero(Element element) {
            switch (element) {
                case EXPLOSION:       return EXPLOSION;
                case HERO_LEFT:       return ENEMY_HERO_LEFT;
                case HERO_RIGHT:      return ENEMY_HERO_RIGHT;
                case HERO_UP:         return ENEMY_HERO_UP;
                case HERO_DOWN:       return ENEMY_HERO_DOWN;
                case HERO_SIDE_LEFT:  return ENEMY_HERO_SIDE_LEFT;
                case HERO_SIDE_RIGHT: return ENEMY_HERO_SIDE_RIGHT;
            }
            throw new IllegalArgumentException("Bad hero state: " + element);
        }
    };
}