package co.dev.web.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.dev.dao.UserDAO;
import co.dev.service.ReviewService;
import co.dev.vo.UserVO;
import co.dev.web.Controller;

public class UserFindControl implements Controller {

	String job;
	
	public UserFindControl(String job) {
		this.job = job;
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String totalTel = "";
		int userTel = 0;
		String userId = "";
		
		ReviewService reService = new ReviewService();
		HttpSession session = request.getSession();


		// 아이디 찾기
		if (job.equals("findId")) {
			

			String tel1 = request.getParameter("tel1");
			String tel2 = request.getParameter("tel2");
			String tel3 = request.getParameter("tel3");
			totalTel = tel1 + tel2 + tel3;
			userTel = Integer.valueOf(totalTel);
			
			String findId = reService.userIdSelect(userTel);
		
			if (findId == null) {
				request.setAttribute("error", "입력하신 정보로 조회되는 아이디가 없습니다.");
				request.getRequestDispatcher("/view/user/findId.tiles").forward(request, response);
				return;
				
			} else {
				request.setAttribute("findId", findId);
				request.getRequestDispatcher("/view/user/findIdResult.tiles").forward(request, response);
				
			}

		// 비밀번호 찾기 > 아이디 검색
		} else if (job.equals("findIdForPwd")) {
			
			String inputId = request.getParameter("id");
			UserVO uvo = reService.userSelect(inputId);
			

			if(uvo == null) {
				request.setAttribute("error", "입력하신 정보로 조회되는 아이디가 없습니다.");
				request.getRequestDispatcher("/view/user/findPwd.tiles").forward(request, response);
				return;
			} else {
				userId = uvo.getId();
				session.setAttribute("inputId", userId);
				request.getRequestDispatcher("/mailSend.do").forward(request, response);
				return;
			}

			
		// 임시 비밀번호 발급
		} else if (job.equals("makeTempPwd")) {
			
			String key = (String) session.getAttribute("key");
			userId = (String) session.getAttribute("inputId");
			String inputKey = request.getParameter("input_key");
			
			if (key.equals(inputKey)) {
				
				String tempPwd = UserFindControl.createTempPwd();
				
				UserDAO dao = new UserDAO();
				dao.updatePwd(tempPwd, userId);
				
				request.setAttribute("tempPwd", tempPwd);
				request.getRequestDispatcher("/view/user/findPwdResult.tiles").forward(request, response);
			} else {
				request.setAttribute("error", "인증코드가 일치하지 않습니다.");
				request.getRequestDispatcher("/view/user/findPwdKey.tiles").forward(request, response);
				return;
			}

			
		}

	}
	
	// 임시 비밀번호 생성
	public static String createTempPwd() {
		char[] charSet = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N',
				'O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		StringBuffer tempPwd = new StringBuffer();
		
		for (int i = 0; i < 10; i++) {
			int idx = (int) (charSet.length*Math.random());
			tempPwd.append(charSet[idx]);
		}
		
		return tempPwd.toString();
	}

}
