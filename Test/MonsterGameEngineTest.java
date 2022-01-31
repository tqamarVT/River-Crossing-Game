package river;

import org.junit.Assert;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

public class MonsterGameEngineTest {
    GameEngine engine;

    private final static Item M1 = Item.ITEM_0;
    private final static Item M2 = Item.ITEM_2;
    private final static Item M3 = Item.ITEM_4;
    private final static Item K1 = Item.ITEM_1;
    private final static Item K2 = Item.ITEM_3;
    private final static Item K3 = Item.ITEM_5;

    @Before
    public void setUp() throws Exception {
        engine = new MonsterGameEngine();
    }

    @Test
    public void testObjectCallThroughs() {
        Assert.assertEquals("M1", engine.getItemLabel(M1));
        Assert.assertEquals(Location.START, engine.getItemLocation(M1));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(M1));

        Assert.assertEquals("M2", engine.getItemLabel(M2));
        Assert.assertEquals(Location.START, engine.getItemLocation(M2));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(M2));

        Assert.assertEquals("M3", engine.getItemLabel(M3));
        Assert.assertEquals(Location.START, engine.getItemLocation(M3));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(M3));

        Assert.assertEquals(Location.START, engine.getBoatLocation());
    }

    @Test
    public void testMidTransport() {
        Assert.assertEquals(Location.START, engine.getItemLocation(M1));
        engine.loadBoat(M1);
        engine.loadBoat(M2);
        engine.rowBoat();
        engine.unloadBoat(M1);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(M1));
        engine.resetGame();
        engine.rowBoat();
        Assert.assertEquals(Location.START, engine.getItemLocation(M1));
    }

    @Test
    public void testLosingGameLeftShore() {
        // CASE 1
        transport(K1);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(K1));
        Assert.assertTrue(engine.gameIsLost());

        // CASE 2
        engine.resetGame();
        engine.loadBoat(M1);
        engine.loadBoat(K1);
        engine.rowBoat();
        engine.unloadBoat(K1);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(K1));
        Assert.assertFalse(engine.gameIsLost());
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());

        // CASE 3
        engine.resetGame();
        engine.loadBoat(K2);
        engine.loadBoat(K2);
        Assert.assertFalse(engine.gameIsLost());
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());

    }

    @Test
    public void testLosingGameRightShore() {
        // CASE 1
        engine.loadBoat(M1);
        engine.loadBoat(K1);
        engine.rowBoat();
        engine.unloadBoat(M1);
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        engine.loadBoat(M3);
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
    }

    @Test
    public void testNumOfItems() {
        Assert.assertEquals(6, engine.numberOfItems());
    }

    private void transport(Item item) {
        engine.loadBoat(item);
        engine.rowBoat();
        engine.unloadBoat(item);

    }

}
