package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import item.SharedUser_VO;
import item.User_VO;
import model.SharedUser_DAO;
import model.User_Dao;

class SharedUser_Test {

	@Test
	void testAddSharedUser() {
		 SharedUser_VO sharedUser_VO=new SharedUser_VO();
		 sharedUser_VO.setUser_id("a");
		 sharedUser_VO.setShareduser_name("kim");
		 sharedUser_VO.setShareduser_phone_number("01022298");
		 Gson gson=new Gson();
		 String json=gson.toJson(sharedUser_VO);
		 SharedUser_DAO sharedUser_DAO=new SharedUser_DAO();
		 String result=sharedUser_DAO.addSharedUser(json);
		 assertEquals("success", result);
		 System.out.println(result);
	}

}
