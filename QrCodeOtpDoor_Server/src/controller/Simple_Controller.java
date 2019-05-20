package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Door_Dao;
import model.Message_DAO;
import model.DoorUserRelation_DAO;
import model.SharedUser_DAO;
import model.User_Dao;

public class Simple_Controller extends HttpServlet {
	public void doGet(HttpServletRequest request, 
			HttpServletResponse response)
					throws ServletException, IOException {
		processRequest(request, response);
	}

	public void doPost(HttpServletRequest request, 
			HttpServletResponse response)
					throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {
		// 2단계, 요청 파악
		// request 객체로부터 사용자의 요청을 파악하는 코드
		String type = request.getParameter("type");
		String data= request.getParameter("data");
		User_Dao user_Dao=new User_Dao();
		Door_Dao door_Dao=new Door_Dao();
		DoorUserRelation_DAO doorUserRelation_DAO=new DoorUserRelation_DAO();
		SharedUser_DAO sharedUser_DAO=new SharedUser_DAO();
		Message_DAO message_DAO=new Message_DAO();
		// 3단계, 요청한 기능을 수행한다.
		// 사용자에 요청에 따라 알맞은 코드
		Object resultObject = null;
		switch (type) {
		case "login":
			resultObject = user_Dao.login(data);
			break;
		case "addUser":
			resultObject=user_Dao.addUser(data);
			break;
		case "changeUserInfo":
			resultObject=user_Dao.changeUserInfo(data);
			break;
		case "changePasswd":
			resultObject=user_Dao.changePasswd(data);
			break;
		case "deleteUser":
			resultObject=user_Dao.deleteUser(data);
			break;
		case "addSharedUser":
			resultObject=sharedUser_DAO.addSharedUser(data);
			break;
		case "addDoor":
			resultObject=door_Dao.addDoor(data);
			break;
		case "getDoorLocation":
			resultObject=door_Dao.getDoorLocation(data);
			break;
		case "deleteDoor":
			resultObject=door_Dao.deleteDoor(data);
			break;
		case "addDoorUser":
			resultObject=doorUserRelation_DAO.addDoorUser(data);
			break;
		case "deleteDoorUSer":
			resultObject=doorUserRelation_DAO.deleteDoorUser(data);
			break;
		case "requestKey":
			resultObject=doorUserRelation_DAO.requestKey(data);
			break;
		case "compareKey":
			resultObject=doorUserRelation_DAO.compareKey(data);
			break;
		case "getDoorUserInfo":
			resultObject=doorUserRelation_DAO.getDoorUserInfo(data);
			break;
		case "getMsg":
			resultObject=message_DAO.getMsg(data);
			break;
		default:
			resultObject="오류";
			break;
		}
		// 4단계, request나 session에 처리 결과를 저장
		request.setAttribute("result", resultObject);

		// 5단계, RequestDispatcher를 사용하여 알맞은 뷰로 포워딩
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/Simple_View.jsp");
		dispatcher.forward(request, response);
	}


}
