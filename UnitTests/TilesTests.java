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
import Model.YellowTile;
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
	 regularTile = new Tile(location, PrimaryColor.BLACK);
	 factory = new ColoredTilesFactory();
	}

//	/**
//	 * @throws java.lang.Exception
//	 */
//	@After
//	public void tearDown() throws Exception {
//	}

	@Test
	public void tileInstanceChangesToYellowTile() {
		Tile newTile = factory.getColoredTile(regularTile, null);
		System.out.println(newTile);
		assertTrue("created Tile is not instance of YellowTile", newTile instanceof YellowTile);
		
	}

}
