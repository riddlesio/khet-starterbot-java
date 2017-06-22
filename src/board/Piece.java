/*
 *  Copyright 2017 riddles.io (developers@riddles.io)
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 *      For the full copyright and license information, please view the LICENSE
 *      file that was distributed with this source code.
 */

package board;

import java.awt.*;

/**
 * board.Piece - Created on 13-3-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class Piece {

    private PieceType type;
    private String playerId;
    private PieceOrientation orientation;
    private Point coordinate;

    Piece(PieceType type, String playerId, PieceOrientation orientation) {
        this.type = type;
        this.playerId = playerId;
        this.orientation = orientation;
    }

    public static Piece fromString(String string) {
        if (string.length() != 4) {
            return null;
        }

        PieceType type = PieceType.fromString(string.substring(0, 2));
        String playerId = string.substring(2, 3);
        PieceOrientation orientation = PieceOrientation.fromString(string.substring(3));

        return new Piece(type, playerId, orientation);
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    public PieceType getType() {
        return this.type;
    }

    public String getPlayerId() {
        return this.playerId;
    }

    public PieceOrientation getOrientation() {
        return this.orientation;
    }

    public Point getCoordinate() {
        return this.coordinate;
    }
}
