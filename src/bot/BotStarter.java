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

package bot;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import board.Piece;
import board.PieceType;
import move.AbstractMove;
import move.MoveMove;
import move.TurnMove;
import move.TurnType;

/**
 * bot.BotStarter - Created on 13-3-17
 *
 * Magic happens here. You should edit this file, or more specifically
 * the doMove() method to make your bot do more than random moves.
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class BotStarter {

    private Random random;

    private BotStarter() {
        this.random = new Random();
    }

    /**
     * You should edit this method to make your bot smarter.
     * Currently randomly performs either a piece move or a piece turn.
     * @param state The current bot state
     * @return A random move
     */
    public AbstractMove doMove(BotState state) {
        AbstractMove move = null;
        double pMoveType = this.random.nextDouble();
        ArrayList<Piece> myPieces = new ArrayList<>(state.getBoard().getMyPieces());

        while (move == null && myPieces.size() > 0) {
            Piece randomPiece = myPieces.remove(this.random.nextInt(myPieces.size()));

            if (pMoveType < 0.5) {
                move = doRandomMoveMove(state, randomPiece);
            } else {
                move = doRandomTurnMove(state, randomPiece);
            }
        }

        return move;
    }

    private AbstractMove doRandomMoveMove(BotState state, Piece piece) {
        if (piece.getType() == PieceType.SPHINX) return null;

        ArrayList<Point> validMoves = state.getBoard().getValidPointsForPiece(piece);

        if (validMoves.size() <= 0) return null;

        Point randomTo = validMoves.get(this.random.nextInt(validMoves.size()));

        return new MoveMove(piece.getCoordinate(), randomTo);
    }

    private AbstractMove doRandomTurnMove(BotState state, Piece piece) {
        ArrayList<TurnType> validTurns = state.getBoard().getValidTurnsForPiece(piece);

        if (validTurns.size() <= 0) return null;

        TurnType randomTurn = validTurns.get(this.random.nextInt(validTurns.size()));

        return new TurnMove(piece.getCoordinate(), randomTurn);
    }

    public static void main(String[] args) {
        BotParser parser = new BotParser(new BotStarter());
        parser.run();
    }
}
