package csc.kth.adk14;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LazyHashTest {
	
	public static class HashCode {

		@Test
		public void testNormal() {
			assertEquals(900*1 + 30*1 + 1, LazyHash.hash("aaa"));
		}
		
		@Test
		public void testOneChar() {
			assertEquals(900*1, LazyHash.hash("a"));
		}
		
		@Test
		public void testPositions() {
			assertEquals(900*1, LazyHash.hash("a  "));
			assertEquals(30*1, LazyHash.hash(" a "));
			assertEquals(1, LazyHash.hash("  a"));
		}
		
		@Test
		public void testEnglishAlphabet() {
			char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
			for (int i = 0; i < alphabet.length; i++) {
				int val = i+1;
				assertEquals(val*900, LazyHash.hash(String.valueOf(alphabet[i])));				
			}
		}
		
		@Test
		public void testNoChars() {
			assertEquals(0, LazyHash.hash(""));
		}
		
		@Test
		public void testSwedishChars() {
			assertEquals(900*29, LazyHash.hash("ö"));
			assertEquals(900*28, LazyHash.hash("ä"));
			assertEquals(900*27, LazyHash.hash("å"));
		}
		
		@Test
		public void testLongString() {
			assertEquals(900*12 + 30*15 + 14, LazyHash.hash("longstring"));
		}
		
		@Test
		public void testCaps() {
			assertEquals(900*3 + 30*1 + 16, LazyHash.hash("CAP"));
		}
		
		@Test
		public void testSpaces() {
			assertEquals(0, LazyHash.hash("   "));
			assertEquals(0, LazyHash.hash("  "));
			assertEquals(0, LazyHash.hash(" "));
		}
	}
	
	public static class IndexArray {
		int[] indexArray;
		
		private void printArray() {
			for (int i = 0; i < indexArray.length; i++) {
				System.out.println(i + ": " + indexArray[i]);
			}
		}
		
		@Before
		public void setup() throws Exception {
			String PATH = "/afs/nada.kth.se/home/i/u1k3g18i/projects/adk14/test_indexarray.txt";
			indexArray = LazyHash.indexArrfromL(PATH);	
		}
		
		@After
		public void teardown() {
			indexArray = null;
		}
		
		@Test
		public void testFirst() {
			printArray();
			assertEquals(0, indexArray[LazyHash.hash("ank")]);
		}
	}



}
