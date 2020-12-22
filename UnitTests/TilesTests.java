import static org.junit.Assert.*;

import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Model.ColoredTilesFactory;
import Model.Location;
import Model.Tile;
import Utils.PrimaryColor;

/**
 * 
 */

/**
 * @author aseel
 *
 */
public class TilesTests {
	
	private Tile regularTile;
	private ColoredTilesFactory factory;

//	/**
//	 * @throws java.lang.Exception
//	 */
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Location location= new Location(5, 'A');
	 regularTile = new Tile.Builder(location, PrimaryColor.BLACK).build();
	 factory = new ColoredTilesFactory();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		regularTile=null;
		factory=null;
	}

	
	@Test
	public void createTileInFactory() {
		try {
		Tile newTile = factory.getColoredTile(regularTile, null);
		System.out.println(newTile);
		}catch (NullPointerException e) {
			e.printStackTrace();
			fail("factory must accept null value as secondary color");
		}
		
		
	}
	
//	@Test
//	public void tileInstanceChangesToYellowTile() {
//		Tile newTile = factory.getColoredTile(regularTile, SeconderyTileColor.YELLOW);
//		System.out.println(newTile);
//		assertTrue("created Tile is not instance of YellowTile", newTile instanceof YellowTile);
//		
//	}

}
