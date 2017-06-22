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

package move;

import java.awt.Point;

/**
 * move.Move - Created on 13-3-17
 *
 * Stores a move action and can transform it to a
 * string that can be returned to the game engine
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public abstract class AbstractMove {

    protected Point from;
    protected MoveType moveType;

    public AbstractMove(Point from) {
        this.from = from;
    }

    public abstract String toString();

    public String pointToString(Point point) {
        return String.format("%d,%d", point.x, point.y);
    }
}
