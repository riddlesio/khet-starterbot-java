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

import java.awt.Point;
import java.util.ArrayList;

import move.TurnType;

/**
 * board.Board - Created on 13-3-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class Board {

    private String myId;
    private String opponentId;
    private int width;
    private int height;

    private Piece[][] fields;
    private String[][] boardLayout;  // Contains what the board looks like without pieces, doesn't change
    private ArrayList<Piece> myPieces;

    public Board() {}

    /**
     * Parses the input string given by the engine
     * @param input String representation of the board
     */
    public void parseFromString(String input) {
        this.fields = new Piece[this.width][this.height];
        this.boardLayout = new String[this.width][this.height];
        this.myPieces = new ArrayList<>();
        int x = 0;
        int y = 0;

        for (String fieldString : input.split(",")) {
            fieldString = fieldString.trim();

            this.boardLayout[x][y] = fieldString.substring(0, 1);
            Piece piece = Piece.parsePiece(fieldString.substring(1), new Point(x, y));

            this.fields[x][y] = piece;

            if (piece != null && piece.getPlayerId().equals(this.myId)) {
                this.myPieces.add(piece);
            }

            if (++x == this.width) {
                x = 0;
                y++;
            }
        }
    }

    public ArrayList<Point> getValidPointsForPiece(Piece piece) {
        ArrayList<Point> validPointsForPiece = new ArrayList<>();
        Point coordinate = piece.getCoordinate();

        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dy == 0 && dx == 0) continue;

                Point validPoint = new Point(coordinate.x + dx, coordinate.y + dy);

                if (!isPointInBounds(validPoint) || !isPointOnLegalField(validPoint)) continue;

                Piece occupying = this.fields[validPoint.x][validPoint.y];
                if (occupying == null || canPieceSwitch(piece, occupying)) {
                    validPointsForPiece.add(validPoint);
                }
            }
        }

        return validPointsForPiece;
    }

    public ArrayList<TurnType> getValidTurnsForPiece(Piece piece) {
        ArrayList<TurnType> validTurnsForPiece = new ArrayList<>();
        validTurnsForPiece.add(TurnType.CLOCKWISE);
        validTurnsForPiece.add(TurnType.COUNTERCLOCKWISE);

        if (piece.getType() != PieceType.SPHINX) {
            return validTurnsForPiece;
        }

        // The sphinx cannot turn away from the board
        for (TurnType turnType : new ArrayList<>(validTurnsForPiece)) {
            Point from = piece.getCoordinate();
            PieceOrientation turned = piece.getOrientation().turn(turnType);
            Point direction = turned.getDirection();
            Point pieceFront = new Point(from.x + direction.x, from.y + direction.y);

            if (!isPointInBounds(pieceFront)) {
                validTurnsForPiece.remove(turnType);
            }
        }

        return validTurnsForPiece;
    }

    private boolean isPointInBounds(Point point) {
        return point.x >= 0 && point.y >=0 && point.x < this.width && point.y < this.height;
    }

    private boolean isPointOnLegalField(Point point) {
        return !this.boardLayout[point.x][point.y].equals(this.opponentId);
    }

    private boolean canPieceSwitch(Piece piece, Piece occupying) {
        if (piece.getType() != PieceType.SCARAB) return false;
        return occupying.getType() == PieceType.PYRAMID || occupying.getType() == PieceType.ANUBIS;
    }

    public void setMyId(int id) {
        this.myId = id + "";
    }

    public void setOpponentId(int id) {
        this.opponentId = id + "";
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMyId() {
        return this.myId;
    }

    public String getOpponentId() {
        return this.opponentId;
    }

    public ArrayList<Piece> getMyPieces() {
        return this.myPieces;
    }
}
