package river;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class for the GameEngine interface which implements all the default
 * methods of the GameEngine interface.
 * 
 * @author Taimoor Qamar
 *
 */
public abstract class AbstractGameEngine implements GameEngine {
    // FIELDS
    protected Map<Item, GameObject> objects;
    protected int numBoatObjects;
    protected Location boatLocation;

    /**
     * Constructor for this class. Initializes the HashMap utilized to hold the
     * game-objects based on their Item Enumeration, sets the boat location to
     * start, and initializes the number of objects in the boat to 0.
     */
    public AbstractGameEngine() {
        objects = new HashMap<Item, GameObject>();
        boatLocation = Location.START;
        numBoatObjects = 0;
    }

    /**
     * {@inheritDoc}
     */
    public String getItemLabel(Item item) {
        return objects.get(item).getLabel();
    }

    /**
     * {@inheritDoc}
     */
    public Color getItemColor(Item item) {
        return objects.get(item).getColor();
    }

    /**
     * {@inheritDoc}
     */
    public Location getItemLocation(Item item) {
        return objects.get(item).getLocation();
    }

    /**
     * {@inheritDoc}
     */
    public void setItemLocation(Item item, Location location) {
        objects.get(item).setLocation(location);
    }

    /**
     * {@inheritDoc}
     */
    public Location getBoatLocation() {
        return boatLocation;
    }

    /**
     * {@inheritDoc}
     */
    public void loadBoat(Item item) {
        if (numBoatObjects > 1) {
            return;
        }
        if (getItemLocation(item) == boatLocation) {
            setItemLocation(item, Location.BOAT);
            numBoatObjects++;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void unloadBoat(Item item) {
        if (getItemLocation(item) == Location.BOAT) {
            setItemLocation(item, boatLocation);
            numBoatObjects--;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void rowBoat() {
        if (numBoatObjects < 1) {
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
    public boolean gameIsWon() {
        for (Map.Entry<Item, GameObject> GameObject : objects.entrySet()) {
            if (getItemLocation(GameObject.getKey()) != Location.FINISH) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean gameIsLost() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public void resetGame() {
        objects.forEach((item, gameObject) -> gameObject.setLocation(Location.START));
        boatLocation = Location.START;
    }

}
