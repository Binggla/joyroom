package co.dev.web.review;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.dev.service.ReviewService;
import co.dev.vo.CafeVO;
import co.dev.vo.MyReviewVO;
import co.dev.vo.UserVO;
import co.dev.web.Controller;

public class ReviewListControl implements Controller {

	String job;

	public ReviewListControl(String job) {
		this.job = job;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		UserVO vo = (UserVO) session.getAttribute("user");
		
		String userId = null;
		if(vo != null) {
			userId = vo.getId();
		}
		
		ReviewService service = new ReviewService();

		if (job.equals("review")) {

			CafeVO cvo = (CafeVO) request.getAttribute("cafeinfo");

			List<MyReviewVO> reviewList = service.reviewList(cvo.getNo(), userId);

			if (reviewList != null) {

				request.setAttribute("cafeinfo", cvo);
				request.setAttribute("reviewList", reviewList);
				request.getRequestDispatcher("/view/cafe/cafeInfo.tiles").forward(request, response);

			}

		} else if (job.equals("myReview")) {

			List<MyReviewVO> myList = service.myReviewList(userId);
			
			if (myList != null) {
			
			request.setAttribute("myReviewList", myList);
			request.getRequestDispatcher("/view/review/myReview.tiles").forward(request, response);
			
			}
		}


	}

}
