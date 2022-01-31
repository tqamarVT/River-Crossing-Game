package river;

import java.awt.Color;
import java.util.Map;

public class MonsterGameEngine extends AbstractGameEngine {

    // FIELDS

    private final static Item M1 = Item.ITEM_0;
    private final static Item M2 = Item.ITEM_2;
    private final static Item M3 = Item.ITEM_4;
    private final static Item K1 = Item.ITEM_1;
    private final static Item K2 = Item.ITEM_3;
    private final static Item K3 = Item.ITEM_5;

    /**
     * Constructor for this class. Populates the HashMap available from the
     * superclass with the game-objects for three monsters and three munchkins.
     */
    public MonsterGameEngine() {
        objects.put(M1, new GameObject("M1", Location.START, Color.CYAN));
        objects.put(M2, new GameObject("M2", Location.START, Color.CYAN));
        objects.put(M3, new GameObject("M3", Location.START, Color.CYAN));
        objects.put(K1, new GameObject("K1", Location.START, Color.YELLOW));
        objects.put(K2, new GameObject("K2", Location.START, Color.YELLOW));
        objects.put(K3, new GameObject("K3", Location.START, Color.YELLOW));
    }

    @Override
    /**
     * Method to check if the game is lost. Returns true whenever the total number
     * of monsters on any side of the shore (left or right) out numbers the total
     * number of munchkins on any side of the shore. If the boat is on the left or
     * right side of the shore, and is holding a monster or munchkin, the objects in
     * the boat are also included in the calculation for total number of objects on
     * left or right side of the shore, with the side of the shore determined by the
     * boats location.
     * 
     * @return boolean: True if game is lost, false if game is active (not lost).
     */
    public boolean gameIsLost() {
        int leftShoreMonsterTotal;
        int leftShoreMunchkinTotal;
        int rightShoreMonsterTotal;
        int rightShoreMunchkinTotal;
        int[] leftBoat = new int[2];
        int[] rightBoat = new int[2];

        int[] leftShore = checkLeftShore();
        int[] rightShore = checkRightShore();

        if (numBoatObjects > 0) {
            if (getBoatLocation() == Location.START) {
                leftBoat = checkLeftBoat();
            }

            if (getBoatLocation() == Location.FINISH) {
                rightBoat = checkRightBoat();
            }
        }

        leftShoreMonsterTotal = leftShore[0] + leftBoat[0];
        rightShoreMonsterTotal = rightShore[0] + rightBoat[0];
        leftShoreMunchkinTotal = leftShore[1] + leftBoat[1];
        rightShoreMunchkinTotal = rightShore[1] + rightBoat[1];

        if ((leftShoreMonsterTotal > leftShoreMunchkinTotal) && leftShoreMunchkinTotal > 0) {
            return true;
        } else if ((rightShoreMonsterTotal > rightShoreMunchkinTotal) && rightShoreMunchkinTotal > 0) {
            return true;
        }
        return false;
    }

    @Override
    /**
     * Returns the total number of objects within this game. Should always return 6
     * for MonsterGameEngine.
     * 
     * @return int : number of objects in the game.
     */
    public int numberOfItems() {
        return objects.size();
    }

    /**
     * Private helper method to determine the total number of munchkins and monsters
     * on the left side of the shore.
     * 
     * @return : int[] A two element array for which the first element is the total
     *         number of monsters on the left side of the shore and the second
     *         element is the total number of munchkins on the left side of the
     *         shore.
     */
    private int[] checkLeftShore() {
        int[] leftShore = new int[2];
        leftShore[0] = 0;
        leftShore[1] = 0;

        for (Map.Entry<Item, GameObject> leftShoreMonsterCheck : objects.entrySet()) {
            if (getItemLabel(leftShoreMonsterCheck.getKey()).contains("M")
                    && (getItemLocation(leftShoreMonsterCheck.getKey()) == Location.START)) {
                leftShore[0] += 1;
            }
        }

        for (Map.Entry<Item, GameObject> leftShoreMunchkinCheck : objects.entrySet()) {
            if (getItemLabel(leftShoreMunchkinCheck.getKey()).contains("K")
                    && (getItemLocation(leftShoreMunchkinCheck.getKey()) == Location.START)) {
                leftShore[1] += 1;
            }
        }

        return leftShore;
    }

    /**
     * Private helper method to determine the total number of munchkins and monsters
     * on the right side of the shore.
     * 
     * @return : int[] A two element array for which the first element is the total
     *         number of monsters on the right side of the shore and the second
     *         element is the total number of munchkins on the right side of the
     *         shore.
     */
    private int[] checkRightShore() {
        int[] rightShore = new int[2];
        rightShore[0] = 0;
        rightShore[1] = 0;

        for (Map.Entry<Item, GameObject> rightShoreMonsterCheck : objects.entrySet()) {
            if (getItemLabel(rightShoreMonsterCheck.getKey()).contains("M")
                    && (getItemLocation(rightShoreMonsterCheck.getKey()) == Location.FINISH)) {
                rightShore[0] += 1;
            }
        }

        for (Map.Entry<Item, GameObject> rightShoreMunchkinCheck : objects.entrySet()) {
            if (getItemLabel(rightShoreMunchkinCheck.getKey()).contains("K")
                    && (getItemLocation(rightShoreMunchkinCheck.getKey()) == Location.FINISH)) {
                rightShore[1] += 1;
            }
        }
        return rightShore;
    }

    /**
     * Private helper method to determine the total number of munchkins and monsters
     * on the boat when its on the left side of the shore.
     * 
     * @return: int[] A two element array for which the first element is the total
     *          number of monsters on the boat when its docked on the left side of
     *          the shore and the second element is the total number of munchkins on
     *          the boat when its docked on the left side of the shore.
     */
    private int[] checkLeftBoat() {
        int[] leftShore = new int[2];
        leftShore[0] = 0;
        leftShore[1] = 0;

        for (Map.Entry<Item, GameObject> leftShoreMonsterCheck : objects.entrySet()) {
            if (getItemLabel(leftShoreMonsterCheck.getKey()).contains("M")
                    && (getItemLocation(leftShoreMonsterCheck.getKey()) == Location.BOAT)) {
                leftShore[0] += 1;
            }
        }

        for (Map.Entry<Item, GameObject> leftShoreMunchkinCheck : objects.entrySet()) {
            if (getItemLabel(leftShoreMunchkinCheck.getKey()).contains("K")
                    && (getItemLocation(leftShoreMunchkinCheck.getKey()) == Location.BOAT)) {
                leftShore[1] += 1;
            }
        }

        return leftShore;

    }

    /**
     * Private helper method to determine the total number of munchkins and monsters
     * on the boat when its on the right side of the shore.
     * 
     * @return: int[] A two element array for which the first element is the total
     *          number of monsters on the boat when its docked on the right side of
     *          the shore and the second element is the total number of munchkins on
     *          the boat when its docked on the right side of the shore.
     */
    private int[] checkRightBoat() {
        int[] rightShore = new int[2];
        rightShore[0] = 0;
        rightShore[1] = 0;

        for (Map.Entry<Item, GameObject> rightShoreMonsterCheck : objects.entrySet()) {
            if (getItemLabel(rightShoreMonsterCheck.getKey()).contains("M")
                    && (getItemLocation(rightShoreMonsterCheck.getKey()) == Location.BOAT)) {
                rightShore[0] += 1;
            }
        }

        for (Map.Entry<Item, GameObject> rightShoreMunchkinCheck : objects.entrySet()) {
            if (getItemLabel(rightShoreMunchkinCheck.getKey()).contains("K")
                    && (getItemLocation(rightShoreMunchkinCheck.getKey()) == Location.BOAT)) {
                rightShore[1] += 1;
            }
        }

        return rightShore;

    }

}
