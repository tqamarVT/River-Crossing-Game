package river;

import org.junit.Assert;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

public class FarmerGameEngineTest {

    GameEngine engine;

    private final static Item BEANS = Item.ITEM_0;
    private final static Item GOOSE = Item.ITEM_1;
    private final static Item WOLF = Item.ITEM_2;
    private final static Item FARMER = Item.ITEM_3;

    @Before
    public void setUp() throws Exception {
        engine = new FarmerGameEngine();
    }

    @Test
    public void testObjectCallThroughs() {
        engine.getItemLabel(FARMER);
        Assert.assertEquals("", engine.getItemLabel(FARMER));
        Assert.assertEquals(Location.START, engine.getItemLocation(FARMER));
        Assert.assertEquals(Color.MAGENTA, engine.getItemColor(FARMER));

        Assert.assertEquals("G", engine.getItemLabel(GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(GOOSE));

        Assert.assertEquals("B", engine.getItemLabel(BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(BEANS));

        Assert.assertEquals("W", engine.getItemLabel(WOLF));
        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(WOLF));

        Assert.assertEquals(Location.START, engine.getBoatLocation());
    }

    @Test
    public void testMidTransport() {
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        engine.loadBoat(FARMER);
        engine.loadBoat(GOOSE);
        engine.rowBoat();
        engine.unloadBoat(GOOSE);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(GOOSE));
    }

    @Test
    public void testWinningGame() {

        Assert.assertEquals(4, engine.numberOfItems());

        engine.loadBoat(FARMER);
        transport(GOOSE);

        engine.rowBoat();

        transport(WOLF);

        transport(GOOSE);

        transport(BEANS);

        engine.rowBoat();

        transport(GOOSE);
        engine.unloadBoat(FARMER);

        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());
    }

    @Test
    public void testLoadAfterWin() {
        testWinningGame();
        engine.loadBoat(FARMER);
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testLosingGame() {
        engine.loadBoat(FARMER);
        transport(GOOSE);

        engine.rowBoat();

        transport(WOLF);

        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testLosingGameGoose() {
        engine.loadBoat(BEANS);
        Assert.assertFalse(engine.gameIsLost());
    }

    @Test
    public void testLosingGooseAndBeans() {
        engine.loadBoat(FARMER);
        engine.loadBoat(WOLF);
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
    }

    @Test
    public void testError() {

        engine.loadBoat(FARMER);
        transport(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        Location topLoc = engine.getItemLocation(WOLF);
        Location midLoc = engine.getItemLocation(GOOSE);
        Location bottomLoc = engine.getItemLocation(BEANS);
        Location playerLoc = engine.getItemLocation(FARMER);

        engine.loadBoat(WOLF);

        Assert.assertEquals(topLoc, engine.getItemLocation(WOLF));
        Assert.assertEquals(midLoc, engine.getItemLocation(GOOSE));
        Assert.assertEquals(bottomLoc, engine.getItemLocation(BEANS));
        Assert.assertEquals(playerLoc, engine.getItemLocation(FARMER));
    }

    @Test
    public void testEmptyBoat() {
        engine.rowBoat();
        Assert.assertEquals(Location.START, engine.getBoatLocation());
        engine.unloadBoat(FARMER);
        Assert.assertEquals(Location.START, engine.getItemLocation(FARMER));
    }

    @Test
    public void testFullBoat() {
        engine.loadBoat(FARMER);
        engine.loadBoat(GOOSE);
        engine.loadBoat(BEANS);

        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
    }

    @Test
    public void testReset() {
        testWinningGame();
        engine.resetGame();
        Assert.assertEquals(Location.START, engine.getItemLocation(FARMER));
    }

    public void transport(Item item) {
        engine.loadBoat(item);
        engine.rowBoat();
        engine.unloadBoat(item);

    }

}
