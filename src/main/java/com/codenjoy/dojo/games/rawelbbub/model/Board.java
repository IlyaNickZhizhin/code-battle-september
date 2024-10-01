package com.codenjoy.dojo.games.rawelbbub.model;

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


import com.codenjoy.dojo.client.AbstractBoard;
import com.codenjoy.dojo.services.Point;

import java.util.List;

import static com.codenjoy.dojo.games.rawelbbub.model.Element.REEFS;
import static com.codenjoy.dojo.games.rawelbbub.utils.ElementUtils.ais;
import static com.codenjoy.dojo.games.rawelbbub.utils.ElementUtils.barriers;
import static com.codenjoy.dojo.games.rawelbbub.utils.ElementUtils.enemies;
import static com.codenjoy.dojo.games.rawelbbub.utils.ElementUtils.fishnet;
import static com.codenjoy.dojo.games.rawelbbub.utils.ElementUtils.heroes;
import static com.codenjoy.dojo.games.rawelbbub.utils.ElementUtils.icebergs;
import static com.codenjoy.dojo.games.rawelbbub.utils.ElementUtils.oil;
import static com.codenjoy.dojo.games.rawelbbub.utils.ElementUtils.otherHeroes;
import static com.codenjoy.dojo.games.rawelbbub.utils.ElementUtils.prizes;
import static com.codenjoy.dojo.games.rawelbbub.utils.ElementUtils.seaweed;
import static com.codenjoy.dojo.games.rawelbbub.utils.ElementUtils.torpedoes;

/**
 * The class is a wrapper over the board string
 * coming from the server. Contains a number of
 * inherited methods {@link AbstractBoard},
 * but you can add any methods based on them here.
 */
public class Board extends AbstractBoard<Element> {

    @Override
    public Element[] elements() {
        return Element.values();
    }

    @Override
    protected int inversionY(int y) { // TODO разобраться с этим чудом
        return size - 1 - y;
    }

    @Override
    public Element getAt(int x, int y) {
        if (isOutOf(x, y)) {
            return REEFS;
        }
        return super.getAt(x, y);
    }

    public boolean isGameOver() {
        return getHero() == null;
    }

    public Point getHero() {
        return getFirst(heroes);
    }

    public List<Point> getBarriers() {
        return get(barriers);
    }

    public List<Point> getOtherHeroes() {
        return get(otherHeroes);
    }

    public List<Point> getAis() {
        return get(ais);
    }

    public List<Point> getEnemies() {
        return get(enemies);
    }

    public List<Point> getTorpedoes() {
        return get(torpedoes);
    }

    public List<Point> getFishnet() {
        return get(fishnet);
    }

    public List<Point> getOil() {
        return get(oil);
    }

    public List<Point> getIcebergs() {
        return get(icebergs);
    }

    public List<Point> getSeaweed() {
        return get(seaweed);
    }

    public List<Point> getPrizes() {
        return get(prizes);
    }

    public boolean isHeroAt(Point pt) {
        return isAt(pt, heroes);
    }

    public boolean isBarrierAt(Point pt) {
        return isAt(pt, barriers);
    }

    public boolean isOtherHeroAt(Point pt) {
        return isAt(pt, otherHeroes);
    }

    public boolean isAiAt(Point pt) {
        return isAt(pt, ais);
    }

    public boolean isEnemyAt(Point pt) {
        return isAt(pt, enemies);
    }

    public boolean isTorpedoAt(Point pt) {
        return isAt(pt, torpedoes);
    }

    public boolean isFishnetAt(Point pt) {
        return isAt(pt, fishnet);
    }

    public boolean isOilAt(Point pt) {
        return isAt(pt, oil);
    }

    public boolean isIcebergAt(Point pt) {
        return isAt(pt, icebergs);
    }

    public boolean isSeaweedAt(Point pt) {
        return isAt(pt, seaweed);
    }

    public boolean isPrizeAt(Point pt) {
        return isAt(pt, prizes);
    }

    @Override
    public String toString() {
        return String.format("%s\n" +
                        "Hero at: %s\n" +
                        "Enemies at: %s\n" +
                        "Torpedoes at: %s\n" +
                        "Prizes at: %s\n",
                boardAsString(),
                getHero(),
                getEnemies(),
                getTorpedoes(),
                getPrizes());
    }
}