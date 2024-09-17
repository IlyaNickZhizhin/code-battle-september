package com.codenjoy.dojo.games.rawelbbub;

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

import com.codenjoy.dojo.games.rawelbbub.model.Board;
import com.codenjoy.dojo.games.rawelbbub.model.Element;
import org.junit.Before;
import org.junit.Test;

import static com.codenjoy.dojo.services.PointImpl.pt;
import static org.junit.Assert.assertEquals;

public class BoardTest {

    private Board board;

    public static Board board(String boardString) {
        return (Board)new Board().forString(boardString);
    }

    @Before
    public void before() {
        board = board(
               /*14*/"☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
               /*13*/"☼w{}î » ◄ » « ☼\n" +
               /*12*/"☼ ╦ └?─ ─ ╠ ╥?☼\n" +
               /*11*/"☼ø╞ └%╠☼╬~╣#╬ ☼\n" +
               /*10*/"☼ ╞ ╬%┌ ╬~┌#╥ ☼\n" +
                /*9*/"☼▲╬ ╦% ø ~╣#╬!☼\n" +
                /*8*/"☼•   %╬ ╬~ # 1☼\n" +
                /*7*/"☼  Ѡ╬     ╩ ¿2☼\n" +
                /*6*/"☼¤    ╨ ╬˂   3☼\n" +
                /*5*/"☼ ┐ ╬ ╬╩╨ ╬ ╬4☼\n" +
                /*4*/"☼˅╬•╬ ╬ ╬ ╬ ╬5☼\n" +
                /*3*/"☼ ┐  Ѡ ×    ┘ ☼\n" +
                /*2*/"☼•╬?  ╡╬╡ Ѡ ┘¤☼\n" +
                /*1*/"☼  ▼˃ │×│ ˅ ► ☼\n" +
                /*0*/"☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n"
                    /*012345678901234*/);
    }

    @Test
    public void shouldWorkToString() {
        assertEquals(
               "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
               "☼w{}î » ◄ » « ☼\n" +
               "☼ ╦ └?─ ─ ╠ ╥?☼\n" +
               "☼ø╞ └%╠☼╬~╣#╬ ☼\n" +
               "☼ ╞ ╬%┌ ╬~┌#╥ ☼\n" +
               "☼▲╬ ╦% ø ~╣#╬!☼\n" +
               "☼•   %╬ ╬~ # 1☼\n" +
               "☼  Ѡ╬     ╩ ¿2☼\n" +
               "☼¤    ╨ ╬˂   3☼\n" +
               "☼ ┐ ╬ ╬╩╨ ╬ ╬4☼\n" +
               "☼˅╬•╬ ╬ ╬ ╬ ╬5☼\n" +
               "☼ ┐  Ѡ ×    ┘ ☼\n" +
               "☼•╬?  ╡╬╡ Ѡ ┘¤☼\n" +
               "☼  ▼˃ │×│ ˅ ► ☼\n" +
               "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
               "\n" +
               "Hero at: [1,9]\n" +
               "Enemies at: [[1,4], [1,13], [2,13], [3,2], [3,13], [4,1], [4,13], [5,12], [6,13], [9,6], [10,1], [10,13], [12,7], [12,13], [13,12]]\n" +
               "Torpedoes at: [[1,2], [1,6], [1,8], [1,11], [3,4], [7,1], [7,3], [7,9], [13,2]]\n" +
               "Prizes at: [[13,4], [13,5], [13,6], [13,7], [13,8], [13,9]]\n", board.toString());
    }

    @Test
    public void shouldWork_getAt() {
        assertEquals(Element.OTHER_HERO_DOWN, board.getAt(1, 4));
        assertEquals(Element.REEFS, board.getAt(0, 8));
        assertEquals(Element.ICEBERG_SMALL_LEFT_RIGHT, board.getAt(6, 1));
    }

    @Test
    public void shouldWork_getAt_point() {
        assertEquals(Element.OTHER_HERO_DOWN, board.getAt(pt(1, 4)));
        assertEquals(Element.REEFS, board.getAt(pt(0, 8)));
        assertEquals(Element.ICEBERG_SMALL_LEFT_RIGHT, board.getAt(pt(6, 1)));
    }

    @Test
    public void shouldWork_getNear() {
        assertEquals("[•, ▲,  ,  , ╞,  ,  ,  ]", board.getNear(2, 9).toString());
        assertEquals("[%, ?,  , ╠, », ☼,  ,  ]", board.getNear(6, 12).toString());
        assertEquals("[#,  ,  , ╬, «,  , ?,  ]", board.getNear(12, 12).toString());
    }

    @Test
    public void shouldWork_getNear_point() {
        assertEquals("[•, ▲,  ,  , ╞,  ,  ,  ]", board.getNear(pt(2, 9)).toString());
        assertEquals("[%, ?,  , ╠, », ☼,  ,  ]", board.getNear(pt(6, 12)).toString());
        assertEquals("[#,  ,  , ╬, «,  , ?,  ]", board.getNear(pt(12, 12)).toString());
    }

    @Test
    public void shouldWork_getAt_outOfBoard() {
        assertEquals(Element.REEFS, board.getAt(-1, 1));
        assertEquals(Element.REEFS, board.getAt(1, -1));
        assertEquals(Element.REEFS, board.getAt(100, 1));
        assertEquals(Element.REEFS, board.getAt(1, 100));

        assertEquals(Element.REEFS, board.getAt(pt(-1, 1)));
        assertEquals(Element.REEFS, board.getAt(pt(1, -1)));
        assertEquals(Element.REEFS, board.getAt(pt(100, 1)));
        assertEquals(Element.REEFS, board.getAt(pt(1, 100)));
    }

    @Test
    public void shouldWork_isGameOver() {
        assertEquals(false, board.isGameOver());
        assertEquals(true, board(" ").isGameOver());
    }

    @Test
    public void shouldWork_getHero() {
        assertEquals("[1,9]", board.getHero().toString());

        assertEquals(null, board(" ").getHero());
    }

    @Test
    public void shouldWork_isHeroAt_point() {
        assertEquals(true, board.isHeroAt(pt(1, 9)));
        assertEquals(true, board.isHeroAt(pt(3, 1)));
        assertEquals(false, board.isHeroAt(pt(1, 8)));

        assertEquals(false, board(" ").isHeroAt(pt(0, 0)));
    }

    @Test
    public void shouldWork_getBarriers() {
        assertEquals("[[0,0], [0,1], [0,2], [0,3], [0,4], [0,5], [0,6], [0,7], [0,8], " +
                        "[0,9], [0,10], [0,11], [0,12], [0,13], [0,14], [1,0], [1,14], " +
                        "[2,0], [2,2], [2,3], [2,4], [2,5], [2,9], [2,10], [2,11], " +
                        "[2,12], [2,14], [3,0], [3,14], [4,0], [4,4], [4,5], [4,7], " +
                        "[4,9], [4,10], [4,11], [4,12], [4,14], [5,0], [5,14], [6,0], " +
                        "[6,1], [6,2], [6,4], [6,5], [6,6], [6,8], [6,10], [6,11], " +
                        "[6,12], [6,14], [7,0], [7,2], [7,5], [7,11], [7,14], [8,0], " +
                        "[8,1], [8,2], [8,4], [8,5], [8,6], [8,8], [8,10], [8,11], " +
                        "[8,12], [8,14], [9,0], [9,8], [9,9], [9,10], [9,11], " +
                        "[9,14], [10,0], [10,4], [10,5], [10,7], " +
                        "[10,9], [10,10], [10,11], [10,12], [10,14], [11,0], [11,14], " +
                        "[12,0], [12,2], [12,3], [12,4], [12,5], [12,9], [12,10], [12,11], " +
                        "[12,12], [12,14], [13,0], [13,14], [14,0], [14,1], [14,2], " +
                        "[14,3], [14,4], [14,5], [14,6], [14,7], [14,8], [14,9], [14,10], " +
                        "[14,11], [14,12], [14,13], [14,14]]",
                board.getBarriers().toString());
    }

    @Test
    public void shouldWork_isBarrierAt() {
        assertEquals(false, board.isBarrierAt(pt(1, 8)));
        assertEquals(true, board.isBarrierAt(pt(2, 2)));
        assertEquals(false, board.isBarrierAt(pt(-2, 2)));
    }

    @Test
    public void shouldWork_getOtherHeroes() {
        assertEquals("[[1,4], [4,1], [9,6], [10,1]]",
                board.getOtherHeroes().toString());
    }

    @Test
    public void shouldWork_isOtherHeroAt() {
        assertEquals(false, board.isOtherHeroAt(pt(1, 8)));
        assertEquals(true, board.isOtherHeroAt(pt(1, 4)));
        assertEquals(true, board.isOtherHeroAt(pt(4, 1)));
        assertEquals(false, board.isOtherHeroAt(pt(-2, 2)));
    }

    @Test
    public void shouldWork_getAis() {
        assertEquals("[[1,13], [2,13], [3,2], [3,13], [4,13], [5,12], [6,13], " +
                        "[10,13], [12,7], [12,13], [13,12]]",
                board.getAis().toString());
    }

    @Test
    public void shouldWork_isAiAt() {
        assertEquals(false, board.isAiAt(pt(1, 8)));
        assertEquals(true, board.isAiAt(pt(3, 2)));
        assertEquals(true, board.isAiAt(pt(3, 13)));
        assertEquals(false, board.isAiAt(pt(-2, 2)));
    }

    @Test
    public void shouldWork_getEnemies() {
        assertEquals("[[1,4], [1,13], [2,13], [3,2], [3,13], [4,1], [4,13], [5,12], " +
                        "[6,13], [9,6], [10,1], [10,13], [12,7], [12,13], [13,12]]",
                board.getEnemies().toString());
    }

    @Test
    public void shouldWork_isEnemyAt() {
        assertEquals(false, board.isEnemyAt(pt(1, 8)));
        assertEquals(true, board.isEnemyAt(pt(1, 4)));
        assertEquals(true, board.isEnemyAt(pt(10, 1)));
        assertEquals(false, board.isEnemyAt(pt(-2, 2)));
    }

    @Test
    public void shouldWork_getTorpedoes() {
        assertEquals("[[1,2], [1,6], [1,8], [1,11], [3,4], [7,1], [7,3], [7,9], [13,2]]",
                board.getTorpedoes().toString());
    }

    @Test
    public void shouldWork_isTorpedoAt() {
        assertEquals(false, board.isTorpedoAt(pt(1, 9)));
        assertEquals(true, board.isTorpedoAt(pt(1, 2)));
        assertEquals(true, board.isTorpedoAt(pt(1, 8)));
        assertEquals(false, board.isTorpedoAt(pt(-2, 2)));
    }

    @Test
    public void shouldWork_getFishnet() {
        assertEquals("[[9,8], [9,9], [9,10], [9,11]]",
                board.getFishnet().toString());
    }

    @Test
    public void shouldWork_isFishnetAt() {
        assertEquals(false, board.isFishnetAt(pt(1, 9)));
        assertEquals(true, board.isFishnetAt(pt(9, 8)));
        assertEquals(true, board.isFishnetAt(pt(9, 9)));
        assertEquals(false, board.isFishnetAt(pt(-2, 2)));
    }

    @Test
    public void shouldWork_getOil() {
        assertEquals("[[11,8], [11,9], [11,10], [11,11]]",
                board.getOil().toString());
    }

    @Test
    public void shouldWork_isOilAt() {
        assertEquals(false, board.isOilAt(pt(1, 9)));
        assertEquals(true, board.isOilAt(pt(11, 8)));
        assertEquals(true, board.isOilAt(pt(11, 9)));
        assertEquals(false, board.isOilAt(pt(-2, 2)));
    }

    @Test
    public void shouldWork_getIcebergs() {
        assertEquals("[[2,2], [2,3], [2,4], [2,5], [2,9], [2,10], [2,11], [2,12], " +
                        "[4,4], [4,5], [4,7], [4,9], [4,10], [4,11], [4,12], [6,1], " +
                        "[6,2], [6,4], [6,5], [6,6], [6,8], [6,10], [6,11], [6,12], " +
                        "[7,2], [7,5], [8,1], [8,2], [8,4], [8,5], [8,6], [8,8], " +
                        "[8,10], [8,11], [8,12], [10,4], [10,5], [10,7], [10,9], " +
                        "[10,10], [10,11], [10,12], [12,2], [12,3], [12,4], [12,5], " +
                        "[12,9], [12,10], [12,11], [12,12]]",
                board.getIcebergs().toString());
    }

    @Test
    public void shouldWork_isIcebergAt() {
        assertEquals(false, board.isIcebergAt(pt(1, 9)));
        assertEquals(true, board.isIcebergAt(pt(4, 4)));
        assertEquals(true, board.isIcebergAt(pt(6, 2)));
        assertEquals(false, board.isIcebergAt(pt(-2, 2)));
    }

    @Test
    public void shouldWork_getSeaweed() {
        assertEquals("[[5,8], [5,9], [5,10], [5,11]]",
                board.getSeaweed().toString());
    }

    @Test
    public void shouldWork_isSeaweedAt() {
        assertEquals(false, board.isSeaweedAt(pt(1, 9)));
        assertEquals(true, board.isSeaweedAt(pt(5, 8)));
        assertEquals(true, board.isSeaweedAt(pt(5, 9)));
        assertEquals(false, board.isSeaweedAt(pt(-2, 2)));
    }

    @Test
    public void shouldWork_getPrizes() {
        assertEquals("[[13,4], [13,5], [13,6], [13,7], [13,8], [13,9]]",
                board.getPrizes().toString());
    }

    @Test
    public void shouldWork_isPrizeAt() {
        assertEquals(false, board.isPrizeAt(pt(1, 9)));
        assertEquals(true, board.isPrizeAt(pt(13, 4)));
        assertEquals(true, board.isPrizeAt(pt(13, 5)));
        assertEquals(false, board.isPrizeAt(pt(-2, 2)));
    }

    @Test
    public void shouldWork_countNear() {
        assertEquals("[☼, ☼,  ]", board.getNear(0, 0).toString());
        assertEquals(2, board.countNear(0, 0, Element.REEFS));

        assertEquals("[☼, ☼, ☼]", board.getNear(-1, 5).toString());
        assertEquals(0, board.countNear(-1, 5, Element.REEFS));

        assertEquals("[]", board.getNear(-2, 5).toString());
        assertEquals(0, board.countNear(-2, 5, Element.REEFS));

        assertEquals("[╬, ╬,  ,  ,  , ╬, ╬, ╨]", board.getNear(5, 5).toString());
        assertEquals(4, board.countNear(5, 5, Element.ICEBERG_HUGE));
        assertEquals(1, board.countNear(5, 5, Element.ICEBERG_SMALL_DOWN_DOWN));

        assertEquals("[╬, ╨,  , ╩,  , ╨, ╬,  ]", board.getNear(7, 6).toString());
        assertEquals(2, board.countNear(7, 6, Element.ICEBERG_HUGE));
        assertEquals(2, board.countNear(7, 6, Element.ICEBERG_SMALL_DOWN_DOWN));
        assertEquals(3, board.countNear(7, 6, Element.WATER));

        assertEquals("[☼, ☼, ☼, •, ˅, ╬, ┐, ╬]", board.getNear(1, 3).toString());
        assertEquals(1, board.countNear(1, 3, Element.TORPEDO_LEFT));
        assertEquals(3, board.countNear(1, 3, Element.REEFS));
        assertEquals(1, board.countNear(1, 3, Element.ICEBERG_SMALL_UP_RIGHT));
        assertEquals(1, board.countNear(1, 3, Element.OTHER_HERO_DOWN));

        assertEquals("[☼, ☼, ☼, ¤, •,  ,  ,  ]", board.getNear(1, 7).toString());
        assertEquals(1, board.countNear(1, 7, Element.TORPEDO_LEFT));

        assertEquals("[☼, ☼, ☼, ø, w, ╞, ╦, {]", board.getNear(1, 12).toString());
        assertEquals(1, board.countNear(1, 12, Element.TORPEDO_UP));

        assertEquals("[┐, ╬, ┐,  ,  ,  , ╬, ╬]", board.getNear(3, 4).toString());
        assertEquals(0, board.countNear(3, 4, Element.TORPEDO_LEFT));
        assertEquals(3, board.countNear(3, 4, Element.WATER));
        assertEquals(2, board.countNear(3, 4, Element.ICEBERG_SMALL_UP_RIGHT));

        assertEquals("[☼, ☼, ☼,  , ▲,  ,  , ╬]", board.getNear(1, 8).toString());
        assertEquals(0, board.countNear(1, 8, Element.TORPEDO_LEFT));

        assertEquals("[│, ╡,  , ×, ×, │, ╡,  ]", board.getNear(7, 2).toString());
        assertEquals(2, board.countNear(7, 2, Element.TORPEDO_DOWN));
        assertEquals(0, board.countNear(7, 2, Element.TORPEDO_LEFT));

        assertEquals("[ , ╬, ╬, ×, ╩,  , ╬, ╨]", board.getNear(7, 4).toString());
        assertEquals(0, board.countNear(7, 4, Element.TORPEDO_LEFT));
        assertEquals(1, board.countNear(7, 4, Element.TORPEDO_DOWN));

        assertEquals("[╦, {, ☼,  , ☼, └, î, ☼]", board.getNear(3, 13).toString());
        assertEquals(3, board.countNear(3, 13, Element.REEFS));

        assertEquals("[?,  , •,  , ╬,  , Ѡ,  ]", board.getNear(4, 3).toString());
        assertEquals(1, board.countNear(4, 3, Element.EXPLOSION));
    }

    @Test
    public void shouldWork_countNear_point() {
        assertEquals(0, board.countNear(pt(0, 0), Element.ICEBERG_HUGE));
        assertEquals(1, board.countNear(pt(2, 1), Element.ICEBERG_HUGE));
        assertEquals(4, board.countNear(pt(5, 5), Element.ICEBERG_HUGE));
        assertEquals(2, board.countNear(pt(7, 6), Element.ICEBERG_HUGE));
    }

    @Test
    public void shouldWork_isAt_point() {
        assertEquals(true, board.isAt(pt(1, 4), Element.OTHER_HERO_DOWN));
        assertEquals(true, board.isAt(pt(0, 8), Element.REEFS));
        assertEquals(false, board.isAt(pt(6, 1), Element.ICEBERG_HUGE));

        assertEquals(false, board.isAt(pt(1, 4), Element.AI_LEFT));
        assertEquals(false, board.isAt(pt(0, 8), Element.AI_LEFT));
        assertEquals(false, board.isAt(pt(6, 1), Element.AI_LEFT));

        assertEquals(true, board.isAt(pt(1, 4), Element.OTHER_HERO_DOWN, Element.AI_LEFT));
        assertEquals(true, board.isAt(pt(0, 8), Element.AI_LEFT, Element.REEFS));
        assertEquals(false, board.isAt(pt(6, 1), Element.ICEBERG_HUGE, Element.AI_LEFT));
    }

    @Test
    public void shouldWork_isAt() {
        assertEquals(true, board.isAt(1, 4, Element.OTHER_HERO_DOWN));
        assertEquals(true, board.isAt(0, 8, Element.REEFS));
        assertEquals(false, board.isAt(6, 1, Element.ICEBERG_HUGE));

        assertEquals(false, board.isAt(1, 4, Element.AI_LEFT));
        assertEquals(false, board.isAt(0, 8, Element.AI_LEFT));
        assertEquals(false, board.isAt(6, 1, Element.AI_LEFT));

        assertEquals(true, board.isAt(1, 4, Element.OTHER_HERO_DOWN, Element.AI_LEFT));
        assertEquals(true, board.isAt(0, 8, Element.AI_LEFT, Element.REEFS));
        assertEquals(false, board.isAt(6, 1, Element.ICEBERG_HUGE, Element.AI_LEFT));
    }

    @Test
    public void shouldWork_isNear() {
        assertEquals(true, board.isNear(1, 1, Element.ICEBERG_HUGE));
        assertEquals(true, board.isNear(9, 3, Element.ICEBERG_HUGE));
        assertEquals(false, board.isNear(12, 7, Element.ICEBERG_HUGE));
    }

    @Test
    public void shouldWork_isNear_point() {
        assertEquals(true, board.isNear(pt(1, 1), Element.ICEBERG_HUGE));
        assertEquals(true, board.isNear(pt(9, 3), Element.ICEBERG_HUGE));
        assertEquals(false, board.isNear(pt(12, 7), Element.ICEBERG_HUGE));
    }
}