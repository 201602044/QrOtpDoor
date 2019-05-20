package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import item.Door_VO;
import item.User_VO;
import model.Door_Dao;
import model.User_Dao;

class Door_Test {

	@Test
	void testAddDoor() throws Exception{
		 Door_VO door_VO=new Door_VO();
		 door_VO.setDoor_id("a");
		 door_VO.setDoor_latitude("37");
		 door_VO.setDoor_longitude("123");
		 Gson gson=new Gson();
		 String json=gson.toJson(door_VO);
		 Door_Dao door_Dao=new Door_Dao();
		 String result=door_Dao.addDoor(json);
		 assertEquals("success", result);
		 System.out.println(result);
	}
	

}
