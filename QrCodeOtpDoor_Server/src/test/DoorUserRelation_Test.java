package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import item.DoorUserRelation_VO;
import model.DoorUserRelation_DAO;

class DoorUserRelation_Test {

	@Test
	void testAddDoorUser() {
		DoorUserRelation_VO doorUserRelation_VO=new DoorUserRelation_VO();
		DoorUserRelation_DAO doorUserRelation_DAO=new DoorUserRelation_DAO();
		Gson gson=new Gson();
		doorUserRelation_VO.setDoor_id("a");
		doorUserRelation_VO.setUser_id("b");
		doorUserRelation_VO.setDoor_name("ppap");
		String json=gson.toJson(doorUserRelation_VO);
		String result=doorUserRelation_DAO.addDoorUser(json);
	}//등록되지않은 유저나 사용자들이 등록하ㅕㄴ 안됨, 따라ㅓ 각각의 테이블에 외래키로 참조함, 각 테이블 키 삭제시 연쇄삭젲
	@Test
	void testRequestKey() {
		DoorUserRelation_VO doorUserRelation_VO=new DoorUserRelation_VO();
		DoorUserRelation_DAO doorUserRelation_DAO=new DoorUserRelation_DAO();
		Gson gson=new Gson();
		doorUserRelation_VO.setDoor_id("a");
		doorUserRelation_VO.setUser_id("b");
		String json=gson.toJson(doorUserRelation_VO);
		String result=doorUserRelation_DAO.requestKey(json);
	}
	//키발급시 1분뒤 삭제되야됨(OTP)
	//junit에서는 쓰레드를 테스트 할수없고, 별도의 환경에서 구현했을때 확인
	//여기에서 실행하면 키는 안지워진다.
	@Test
	void testCompareKey() {
		DoorUserRelation_VO doorUserRelation_VO=new DoorUserRelation_VO();
		DoorUserRelation_DAO doorUserRelation_DAO=new DoorUserRelation_DAO();
		Gson gson=new Gson();
		doorUserRelation_VO.setDoor_id("a");
		doorUserRelation_VO.setUser_id("b");
		doorUserRelation_VO.setDoor_key("");
		String json=gson.toJson(doorUserRelation_VO);
		String result=doorUserRelation_DAO.compareKey(json);
		assertEquals("success", result);
	}
	//올바를시 키바로 삭제후 성공반환, 성공 메세지 전달
	//틀릴시 경고누적
	//올바를시 경고 메세지 전달
	@Test
	void testGetDoorUserInfo() {
		DoorUserRelation_VO doorUserRelation_VO=new DoorUserRelation_VO();
		DoorUserRelation_DAO doorUserRelation_DAO=new DoorUserRelation_DAO();
		Gson gson=new Gson();
		doorUserRelation_VO.setUser_id("b");
		String json=gson.toJson(doorUserRelation_VO);
	}
	
}
