package co.dev.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.dev.vo.CafeVO;
import co.dev.vo.MyReviewVO;
import co.dev.vo.ReviewVO;
import co.dev.vo.UserVO;

public class ReviewDAO extends DAO_mac {


	
	// 관리자페이지 전체 리뷰 리스트
	public List<ReviewVO> selectTotalReview(int pageNum) {
	
		conn();
		String sql = "SELECT * FROM (SELECT rownum rn,  a.* FROM (SELECT * FROM review ORDER BY review_no desc ) a ) WHERE rn >= ? AND rn <= ?";
		
		int startNum = (pageNum-1)*10+1;
		int endNum = pageNum*10;
		List<ReviewVO> list = new ArrayList<>();
		
		try {
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, startNum);
			psmt.setInt(2, endNum);
			rs = psmt.executeQuery();
		
			while (rs.next()) {
				
				ReviewVO vo = new ReviewVO();
				
				vo.setCafeNo(rs.getInt("cafe_no"));
				vo.setNo(rs.getInt("review_no"));
				vo.setContent(rs.getString("review_content"));
				vo.setDate(rs.getString("review_date").substring(0, 10));
				vo.setImg(rs.getString("review_img"));
				vo.setLike(rs.getInt("review_like"));
				vo.setStar(rs.getInt("review_star"));
				vo.setUserId(rs.getString("review_userid"));
				vo.setUserNick(rs.getString("review_usernick"));
				vo.setUserImg(rs.getString("review_userimg"));
				
				list.add(vo);
			}
			
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("리뷰 " + r + "건 조회");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return list;

	}
	
	// 전체 리뷰 카운트
	public int selectReviewCount() {
		conn();
		String sql = "SELECT count(*) as total_review "
				+ "FROM review";
		int totalReview = 0;
		
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			if(rs.next()) {
				totalReview = rs.getInt("total_review");
			}
			
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("총 리뷰 " + r + "건 조회");
				return totalReview;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			disconn();
		}

		return 0;
	}

	
	// 단어로 리뷰 검색
	public List<ReviewVO> selectReviewKeyword(String keyword, int pageNum) {
		conn();
		
		String sql = "SELECT * FROM ("
				+ "       SELECT rownum rn, r.* "
				+ "       FROM (SELECT * FROM review WHERE review_content LIKE ? ORDER BY review_no desc ) r ) "
				+ "       WHERE rn >= ? AND rn <= ?";
		
		int startNum = (pageNum-1)*10+1;
		int endNum = pageNum*10;
		List<ReviewVO> list = new ArrayList<>();
		
		try {
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, "%"+keyword+"%");
			psmt.setInt(2, startNum);
			psmt.setInt(3, endNum);
			
			rs = psmt.executeQuery();
		
			while (rs.next()) {
				
				ReviewVO vo = new MyReviewVO();
				
				vo.setCafeNo(rs.getInt("cafe_no"));
				vo.setNo(rs.getInt("review_no"));
				vo.setUserId(rs.getString("review_userid"));
				vo.setUserNick(rs.getString("review_usernick"));
				vo.setUserImg(rs.getString("review_userimg"));
				vo.setDate(rs.getString("review_date").substring(0, 10));
				vo.setLike(rs.getInt("review_like"));
				vo.setStar(rs.getInt("review_star"));
				vo.setContent(rs.getString("review_content"));
				vo.setImg(rs.getString("review_img"));
				
				list.add(vo);
			}
			
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("리뷰 검색 " + r + "건");
				return list;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return null;
	}

	
	public int selectSearchReviewCount(String keyword) {
		conn();
		String sql = "SELECT count(*) AS review_count FROM review \n"
				+ "WHERE review_content LIKE ?\n"
				+ "ORDER BY review_date DESC";
		
		try {	
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, "%"+keyword+"%");
			
			rs = psmt.executeQuery();
			
			int pageNum = 0;
		
			while (rs.next()) {
				
				pageNum = rs.getInt("review_count");
			}
			
			int r = psmt.executeUpdate();
			if (r>0) {
				return pageNum;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return 0;
	}
	
	
	// 카페 상세 정보 리뷰 리스트 조회
	public List<MyReviewVO> selectReviews(int cafeNo, String userId) {
		
		conn();
		
		String sql = "";
		
		if (userId != null) {
			
			sql = "SELECT cafe_no, r.review_no, review_userid, review_usernick, review_userimg, review_date, review_like, review_star, review_content, review_img, like_check\n"
					+ "FROM (SELECT *\n"
					+ "        FROM review\n"
					+ "        WHERE cafe_no=?) r LEFT OUTER JOIN (SELECT review_no, like_check\n"
					+ "FROM review_like\n"
					+ "where like_user = ?) l\n"
					+ "ON r.review_no = l.review_no \n"
					+ "ORDER BY review_like DESC";
		} else {
			
			sql = "SELECT * FROM review WHERE cafe_no = ? ORDER BY review_like DESC";
		}
		
		
		List<MyReviewVO> list = new ArrayList<>();
		
		try {
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, cafeNo);
			
			if(userId != null) {
				psmt.setString(2, userId);				
			}
			
			rs = psmt.executeQuery();
		
			while (rs.next()) {
				
				MyReviewVO vo = new MyReviewVO();
				
				vo.setCafeNo(rs.getInt("cafe_no"));
				vo.setNo(rs.getInt("review_no"));
				vo.setUserId(rs.getString("review_userid"));
				vo.setUserNick(rs.getString("review_usernick"));
				vo.setUserImg(rs.getString("review_userimg"));
				vo.setDate(rs.getString("review_date").substring(0, 10));
				vo.setLike(rs.getInt("review_like"));
				vo.setStar(rs.getInt("review_star"));
				vo.setContent(rs.getString("review_content"));
				vo.setImg(rs.getString("review_img"));
				
				if(userId != null) {
					
					if (rs.getString("like_check") == null) {
						vo.setLikeCheck("false");
					} else {
						vo.setLikeCheck("true");
					}
				}
				
				list.add(vo);
			}
			
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("리뷰 " + r + "건 조회");
			}
			
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return null;
	}
	
	// 내 리뷰 리스트
	public List<MyReviewVO> myReviewSelect(String userId) {
			
		conn();
		String sql = "select cafe_no, cafe_name, cafe_img, r.review_no, review_userid, review_usernick, review_userimg, review_date, review_like, review_star, review_content, review_img, like_check "
				+ "from (select c.cafe_no, cafe_name, cafe_img, review_no, review_userid, review_usernick, review_userimg, review_date, review_like, review_star, review_content, review_img "
				+ "        from cafe c, review r "
				+ "        where c.cafe_no=r.cafe_no) r LEFT OUTER JOIN (select review_no, like_check "
				+ "from review_like\n"
				+ "where like_user = ?) l "
				+ "ON r.review_no = l.review_no "
				+ "WHERE review_userid = ?"
				+ "ORDER BY review_date desc";
		
		List<MyReviewVO> list = new ArrayList<>();
			
		try {
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userId);
			psmt.setString(2, userId);
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				
				MyReviewVO vo = new MyReviewVO();
					
				vo.setCafeNo(rs.getInt("cafe_no"));
				vo.setCafeName(rs.getString("cafe_name"));
				vo.setCafeImg(rs.getString("cafe_img"));
				vo.setNo(rs.getInt("review_no"));
				vo.setUserId(rs.getString("review_userid"));
				vo.setUserNick(rs.getString("review_usernick"));
				vo.setUserImg(rs.getString("review_userimg"));
				vo.setDate(rs.getString("review_date").substring(0, 10));
				vo.setLike(rs.getInt("review_like"));
				vo.setStar(rs.getInt("review_star"));
				vo.setContent(rs.getString("review_content"));
				vo.setImg(rs.getString("review_img"));
				
				if (rs.getString("like_check") == null) {
					vo.setLikeCheck("false");
				} else {
					vo.setLikeCheck("true");
				}
					
				list.add(vo);
			}
				
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("내 리뷰 " + r + "건 조회");
			}
				
			return list;
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return null;
	}
	
	// 수정할 리뷰 1건 조회
	public ReviewVO selectReview(int reviewNo) {
		
		conn();
		String sql = "SELECT * FROM review WHERE review_no = ?";
			
		ReviewVO vo = new ReviewVO();
			
		try {
				
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, reviewNo);
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				vo.setCafeNo(rs.getInt("cafe_no"));
				vo.setNo(rs.getInt("review_no"));
				vo.setContent(rs.getString("review_content"));
				vo.setDate(rs.getString("review_date").substring(0, 10));
				vo.setImg(rs.getString("review_img"));
				vo.setLike(rs.getInt("review_like"));
				vo.setStar(rs.getInt("review_star"));
				vo.setUserId(rs.getString("review_userid"));
				vo.setUserNick(rs.getString("review_usernick"));

			}
				
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("리뷰 " + r + "건 조회");
			}
				
			return vo;
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return null;
			
	}	
	
	// 리뷰 등록
	public void insertReview(ReviewVO vo) {

		conn();
		String sql = "INSERT INTO review "
				+ "VALUES(?,review_seq.NEXTVAL,?,?,?,SYSDATE,0,?,?,?)";
		String noImgSql = "INSERT INTO review(cafe_no, review_no, review_userid, review_usernick, review_userimg, review_date, review_like, review_star, review_content) " 
				+ "VALUES(?,review_seq.NEXTVAL,?,?,?,SYSDATE,0,?,?)";
		
		try {
			
			if (vo.getImg()!=null) {
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, vo.getCafeNo());
				psmt.setString(2, vo.getUserId());
				psmt.setString(3, vo.getUserNick());
				psmt.setString(4, vo.getUserImg());
				psmt.setInt(5, vo.getStar());
				psmt.setString(6, vo.getContent());
				psmt.setString(7, vo.getImg());
			} else {
				psmt = conn.prepareStatement(noImgSql);
				psmt.setInt(1, vo.getCafeNo());
				psmt.setString(2, vo.getUserId());
				psmt.setString(3, vo.getUserNick());
				psmt.setString(4, vo.getUserImg());
				psmt.setInt(5, vo.getStar());
				psmt.setString(6, vo.getContent());
			}
			
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("리뷰 " + r + "건 입력");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
	}
	
	
	// 리뷰 수정
	public void updateReview(ReviewVO vo) {
			
		conn();
		String sql = "UPDATE review "
				+ "SET review_star=?, review_content=?, review_img=?"
				+ "WHERE review_no=?";
		String imgSql = "UPDATE review "
				+ "SET review_star=?, review_content=?"
				+ "WHERE review_no=?";
			
		try {
				
			if(vo.getImg() != null) {
					
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, vo.getStar());
				psmt.setString(2, vo.getContent());
				psmt.setString(3, vo.getImg());
				psmt.setInt(4, vo.getNo());

			} else {
					
				psmt = conn.prepareStatement(imgSql);
				psmt.setInt(1, vo.getStar());
				psmt.setString(2, vo.getContent());
				psmt.setInt(3, vo.getNo());
					
			}
				
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("리뷰 " + r + "건 수정");
			}
				

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}

	}
	
	// 리뷰 삭제
	public boolean deleteReview(int reviewNo) {
		
		conn();
		String sql = "DELETE review "
				+ "WHERE review_no=?";
		
		try {
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, reviewNo);
			
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("리뷰 " + r + "건 삭제");
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return false;
	}
	
	// 리뷰 좋아요 수 +1
	public void updateLikePlus(int reviewNo) {

		conn();
		String sql = "UPDATE review "
				+ "SET review_like = review_like+1 "
				+ "WHERE review_no = ?";
		
		try {
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, reviewNo);

			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("좋아요 +" + r);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}

	}
	
	// 리뷰 좋아요 수 -1
	public void updateLikeMinus(int reviewNo) {

		conn();
		String sql = "UPDATE review "
				+ "SET review_like = review_like-1 "
				+ "WHERE review_no = ?";
		
		try {
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, reviewNo);
			
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("좋아요 -" + r);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
	}
	
	// 좋아요 정보 추가
	public void insertLike(String userId, int reviewNo) {
		
		conn();
		String sql = "INSERT INTO review_like "
				+ "VALUES(?,?,?)";
		
		try {
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, reviewNo);
			psmt.setString(2, userId);
			psmt.setString(3, "like");
			
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("좋아요");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
	}
	
	// 좋아요 정보 삭제
	public void deleteLike(String userId, int reviewNo) {
		
		conn();
		String sql = "DELETE review_like "
				+ "WHERE review_no=? AND like_user=?";
		
		try {
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, reviewNo);
			psmt.setString(2, userId);
			
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("좋아요 취소");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
	}
	
	// 좋아요 여부 체크
	public boolean selectLike(String userId, int reviewNo) {
		
		conn();
		String sql = "SELECT like_check FROM review_like "
				+ "WHERE review_no=? AND like_user=?";
		
		try {
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, reviewNo);
			psmt.setString(2, userId);
			
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("좋아요 " + r + "건 조회");
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return false;
	}
	
	// 평균 평점, 리뷰 수 구하기
	public float[] selectReviewInfo(int cafeNo) {
		conn();
		String sql = "SELECT round(avg(review_star), 1) as star_avg, count(*) as review_count\n"
				+ "FROM review WHERE cafe_no = ?";
		float[] reviewInfo = new float[2];
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, cafeNo);
			
			rs = psmt.executeQuery();
			while (rs.next()) {
				reviewInfo[0] = rs.getFloat("star_avg");
				reviewInfo[1] = rs.getFloat("review_count");
			}
			
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("리뷰 정보 " + r + "건 조회");
				return reviewInfo;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			disconn();
		}

		return null;
	}
	
	// 평점 개수 세기
	public int[] selectStarCount(int cafeNo) {
		conn();
		String sql = "SELECT review_star, count(*) AS star_count\n"
				+ "FROM review \n"
				+ "WHERE cafe_no = ?\n"
				+ "GROUP BY review_star\n"
				+ "ORDER BY review_star";
		
		int[] starArr = {0,0,0,0,0};
		
		int i = 0;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, cafeNo);
			
			rs = psmt.executeQuery();
			while (rs.next()) {
				int star = rs.getInt("review_star");
				starArr[(star-1)] = rs.getInt("star_count");
				i++;
			}
			
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("평점 " + r + "건 조회");
				return starArr;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			disconn();
		}

		return null;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	// 유저 조회
	public UserVO selectUser(String userId) {
		
		conn();
		String sql = "SELECT * FROM cfn_user "
				+ "WHERE user_id=?";

		UserVO vo = new UserVO();
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userId);
			rs = psmt.executeQuery();
			
			if (rs.next()) {
				vo.setId(rs.getString("user_id"));
				vo.setPwd(rs.getString("user_pwd"));
				vo.setImg(rs.getString("user_img"));
				vo.setNickname(rs.getString("user_nick"));
				vo.setTel(rs.getString("user_tel"));
		
			}
			
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("유저 " + r + "건 조회");
				return vo;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			disconn();
		}

		return null;
	}
	
	// 유저 아이디 조회
	public String selectUserId(int userTel) {
		
		conn();
		String sql = "SELECT user_id FROM cfn_user "
				+ "WHERE user_tel=?";

		String findId = "";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, userTel);
			rs = psmt.executeQuery();
			
			if (rs.next()) {
				findId = rs.getString("user_id");
			}
			
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("유저 " + r + "건 조회");
				return findId;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			disconn();
		}

		return null;
	}
	
	// 유저 비밀번호 조회
	public String selectUserPwd(String userId, int userTel) {
		
		conn();
		String sql = "SELECT user_pwd FROM cfn_user "
				+ "WHERE user_id=? AND user_tel=?";

		String findPwd = "";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userId);
			psmt.setInt(2, userTel);
			rs = psmt.executeQuery();
			
			if (rs.next()) {
				findPwd = rs.getString("user_pwd");
			}
			
			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("유저 " + r + "건 조회");
				return findPwd;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			disconn();
		}

		return null;
	}
	
	// 로그인 정보 조회
	public boolean userForLogin(String userId, String userPwd) {
		
		conn();
		String sql = "SELECT * FROM cfn_user "
				+ "WHERE user_id=? AND user_pwd=?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userId);
			psmt.setString(2, userPwd);

			int r = psmt.executeUpdate();
			if (r>0) {
				System.out.println("로그인 유저 " + r + "건 조회");
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			disconn();
		}

		return false;
	}




	
	
}
