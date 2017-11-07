/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import de.buccaneersdan.NanoBenchmark;
import de.buccaneersdan.TimeTools;
import org.junit.*;

import java.util.Date;

import static org.junit.Assert.*;

@Ignore
public class NanoBenchmarkTest
{
	NanoBenchmark instance;
	
	public NanoBenchmarkTest()
	{
	}
	
	@BeforeClass
	public static void setUpClass()
	{
	}
	
	@AfterClass
	public static void tearDownClass()
	{
	}
	
	@Before
	public void setUp()
	{
		instance = new NanoBenchmark();
		instance.start();
		try { Thread.sleep(1000L); } catch(InterruptedException e) {}
	}
	
	@After
	public void tearDown()
	{
	}
	
	@Test
	public void testGetElapsed()
	{
		System.out.println("getElapsedMS");
		instance.stop();
		Long expResult = 1L;
		Long result = instance.getElapsedMS() / 1000;
		assertEquals(expResult, result);
	}

	@Test
	public void testGetElapsedDuration()
	{
		System.out.println("getElapsedDuration");
		instance.stop();
		TimeTools.Duration expResult = new TimeTools.Duration(null, null, 1, null);

		TimeTools.Duration result = instance.getElapsedDuration();
		assertEquals(expResult.getSeconds(), result.getSeconds());
	}

	@Test
	public void testGetElapsedDurationH()
	{
		System.out.println("getElapsedDurationH");
		instance.stop();
		String expResult = "0h 0min 1sec";
		String result = instance.getElapsedDurationH();
		assertNotNull(result);
		assertEquals(expResult, result.substring(0, 12));
	}

	@Test
	public void testGetStartTimeH()
	{
		System.out.println("getStartTimeH");
		instance.stop();
		String result = instance.getStartTimeH();
		assertNotNull(result);
	}

	@Test
	public void testGetStartTime()
	{
		System.out.println("getStartTime");
		instance.stop();
		Date result = instance.getStartTime();
		assertNotNull(result);
	}

	@Test
	public void testGetStopTimeH()
	{
		System.out.println("getStopTimeH");
		instance.stop();
		String result = instance.getStopTimeH();
		assertNotNull(result);
	}

	@Test
	public void testGetStopTime()
	{
		System.out.println("getStopTime");
		instance.stop();
		Date result = instance.getStopTime();
		assertNotNull(result);
	}
	
	@Ignore
	@Test
	public void testWriteToFileNoThrow() throws Exception
	{
		System.out.println("writeToFileNoThrow");
		// meh!
		fail("The test case is a prototype.");
	}

	@Ignore
	@Test
	public void testWriteToFile() throws Exception
	{
		System.out.println("writeToFile");
		// meh!
		fail("The test case is a prototype.");
	}
	
}
