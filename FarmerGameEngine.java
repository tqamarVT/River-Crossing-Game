package river;

import java.awt.Color;

public class FarmerGameEngine extends AbstractGameEngine {
    // FIELDS
    private final static Item BEANS = Item.ITEM_0;
    private final static Item GOOSE = Item.ITEM_1;
    private final static Item WOLF = Item.ITEM_2;
    private final static Item FARMER = Item.ITEM_3;

    /**
     * Constructor for this class. Populates the HashMap available from the
     * superclass with the game-objects for beans, goose, wolf, and farmer.
     */
    public FarmerGameEngine() {

        objects.put(BEANS, new GameObject("B", Location.START, Color.CYAN));
        objects.put(GOOSE, new GameObject("G", Location.START, Color.CYAN));
        objects.put(WOLF, new GameObject("W", Location.START, Color.CYAN));
        objects.put(FARMER, new GameObject("", Location.START, Color.MAGENTA));
    }

    /**
     * Method to move the boat from the start (left side of the shore) to the finish
     * (right side of the shore) locations, and vice versa. If Item_3, the farmer
     * game object, does not have the location field "Location.BOAT", the method
     * does nothing and returns.
     */
    @Override
    public void rowBoat() {
        if (getItemLocation(Item.ITEM_3) != Location.BOAT) {
            return;
        }

        if (boatLocation == Location.START) {
            boatLocation = Location.FINISH;
        } else {
            boatLocation = Location.START;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean gameIsLost() {
        if (getItemLocation(GOOSE) == Location.BOAT) {
            return false;
        }
        if (getItemLocation(GOOSE) == getItemLocation(FARMER)) {
            return false;
        }
        if (getItemLocation(GOOSE) == boatLocation) {
            return false;
        }
        if (getItemLocation(GOOSE) == getItemLocation(WOLF)) {
            return true;
        }
        if (getItemLocation(GOOSE) == getItemLocation(BEANS)) {
            return true;
        }
        return false;
    }

    @Override
    /**
     * Returns the number of game objects in this class. Should always return 4.
     * 
     * @return : int Four, the total number of objects in the FarmerGameEngine.
     */
    public int numberOfItems() {
        return objects.size();
    }

}
