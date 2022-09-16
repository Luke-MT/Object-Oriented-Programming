package UPO;

import org.junit.jupiter.api.*;

class TestCasa {
	
	Casa casa;
	
	@Test
	void testMapNotNull() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			new Casa(null);
		});
	}
	
	@Test
	void testMapWrong() {
		
		int[][] map = {{1,1,1,1,1},
					   {1,0,0,0,1}
		              };
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Casa(map);
		});
	}
		
	@BeforeEach
	void setup() throws Exception{
		int[][] map = 
			{{1,1,1,1,1,1,1,1,1,1},
			 {1,0,6,4,0,0,0,0,0,1},
			 {1,0,0,0,0,0,0,0,0,1},
			 {1,0,0,0,0,3,0,0,0,1},
			 {1,0,0,0,0,0,1,0,0,1},
			 {1,0,0,0,0,0,1,0,0,1},
			 {1,0,0,0,0,0,1,0,0,1},
			 {1,8,0,0,0,0,1,0,4,1},
			 {1,0,0,0,0,4,1,0,2,1},
			 {1,1,1,1,1,1,1,1,1,1},
			};
		casa = new Casa(map);
		
	}

	@Test
	void testDirezioneIniziale() {
		Assertions.assertEquals(casa.getRobotDir(), 180);
	}
	
	@Test
	void testTurnLeft() {
		Action left = Action.LEFT;
		casa.turno(left);
		Assertions.assertEquals(casa.getRobotDir(), 270);
		casa.turno(left);
		Assertions.assertEquals(casa.getRobotDir(), 360);
		casa.turno(left);
		Assertions.assertEquals(casa.getRobotDir(), 90);
		casa.turno(left);
		Assertions.assertEquals(casa.getRobotDir(), 180);
	}
	
	@Test
	void testTurnRight() {
		Action right = Action.RIGHT;
		casa.turno(right);
		Assertions.assertEquals(casa.getRobotDir(), 90);
		casa.turno(right);
		Assertions.assertEquals(casa.getRobotDir(), 0);
		casa.turno(right);
		Assertions.assertEquals(casa.getRobotDir(), 270);
		casa.turno(right);
		Assertions.assertEquals(casa.getRobotDir(), 180);
	}
	
	@Test
	void testGo() {
		int[] nextPosExpected = {6,1};
		int[] nextPos = casa.getRobotNextPos();
		Assertions.assertEquals(nextPos[0], nextPosExpected[0]);
		Assertions.assertEquals(nextPos[1], nextPosExpected[1]);
		casa.turno(Action.GO);
		int[] pos = casa.getRobotPos();
		Assertions.assertEquals(pos[0], nextPosExpected[0]);
		Assertions.assertEquals(pos[1], nextPosExpected[1]);		
	}
	
	@Test
	void testNotGo() {
		casa.turno(Action.LEFT);
		int[] nextPosExpected = casa.getRobotPos();
		casa.turno(Action.GO);
		int[] pos = casa.getRobotPos();
		Assertions.assertEquals(pos[0], nextPosExpected[0]);
		Assertions.assertEquals(pos[1], nextPosExpected[1]);
		
	}
	
	@Test
	void testCane() {
		int[] pos = casa.getCanePos();
		casa.turno(Action.GO);
		int[] newPos = casa.getCanePos();
		Assertions.assertEquals( pos[0]-1 <= newPos[0] && newPos[0] <= pos[0]+1, true);
		Assertions.assertEquals( pos[1]-1 <= newPos[1] && newPos[1] <= pos[1]+1, true);		
	}
	
}
