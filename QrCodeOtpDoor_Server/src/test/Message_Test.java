package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import item.Message_VO;
import model.Message_DAO;

class Message_Test {

	@Test
	void testAddMessge() {
		Message_DAO message_DAO=new Message_DAO();
		String[] strings={"a","a","a"};
		String result=message_DAO.addMsg(strings);
		assertEquals("success", result);
	}
//key 중복허용
}
