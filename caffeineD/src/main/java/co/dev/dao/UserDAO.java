package co.dev.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.dev.service.UserService;
import co.dev.vo.CafeVO;
import co.dev.vo.UserVO;

public class UserDAO extends DAO_mac implements UserService {

	// 회원가입
	public void userInsert(UserVO vo) {

		conn();
		String sql = "INSERT INTO cfn_user(user_id, user_pwd, user_nick, user_tel, user_img)" + "VALUES(?,?,?,?,?)";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getId());
			psmt.setString(2, vo.getPwd());
			psmt.setString(3, vo.getNickname());
			psmt.setString(4, vo.getTel());
			psmt.setString(5, vo.getImg());

			int r = psmt.executeUpdate();
			if (r > 0) {
				System.out.println(r + "건 입력");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
	}
	// myPage 내정보찾기
	public UserVO searchInfo(String user_id) {
		conn();
		String sql="select * from cfn_user where user_id=?";
		try {
			psmt=conn.prepareStatement(sql);
			psmt.setString(1, user_id);
			rs= psmt.executeQuery();
			if(rs.next()) {		
				UserVO vo = new UserVO();
				vo.setId(rs.getString("id"));
				vo.setNickname(rs.getString("nickname"));
				vo.setTel(rs.getString("tel"));
				vo.setImg(rs.getString("img"));
				return vo;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return null;
	}
	// myPage 내정보수정
	public void updateInfo(UserVO vo) {
		conn();
		String sql = "update cfn_user set user_pwd=?, user_nick=?, user_tel=? where user_id =?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getPwd());
			psmt.setString(2, vo.getNickname());
			psmt.setString(3, vo.getTel());
			psmt.setString(4, vo.getId());			
			psmt.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
	}


	// 임시비밀번호 발급 -> 아이디값 받아 비밀번호 정보만 변경
	public void updatePwd(String tempPwd, String userId) {

		conn();
		String sql = "UPDATE cfn_user SET user_pwd=? where user_id =?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, tempPwd);
			psmt.setString(2, userId);

			int r = psmt.executeUpdate();
			if (r > 0) {
				System.out.println("임시 비밀번호 변경 완료");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}

	}

	// 북마크 추가
	public void insertBookmark(int cafeNo, String userId) {

		conn();
		String sql = "INSERT INTO bookmark VALUES(?,?)";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, cafeNo);
			psmt.setString(2, userId);

			int r = psmt.executeUpdate();
			if (r > 0) {
				System.out.println("북마크" + r + "건 추가");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
	}

	// 북마크 삭제
	public void deleteBookmark(int cafeNo, String userId) {

		conn();
		String sql = "DELETE FROM bookmark WHERE cafe_no=? AND user_id=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, cafeNo);
			psmt.setString(2, userId);

			int r = psmt.executeUpdate();
			if (r > 0) {
				System.out.println("북마크" + r + "건 삭제");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
	}

	// 내 북마크 조회
	public List<CafeVO> myBookmark(String userId) {

		conn();
		String sql = "SELECT * FROM cafe WHERE cafe_no IN (SELECT cafe_no FROM bookmark WHERE user_id = ?)";

		List<CafeVO> list = new ArrayList<CafeVO>();

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userId);

			rs = psmt.executeQuery();
			while (rs.next()) {

				CafeVO vo = new CafeVO();

				vo.setNo(rs.getInt("cafe_no"));
				vo.setName(rs.getString("cafe_name"));
				vo.setAddress(rs.getString("cafe_address"));
				vo.setTel(rs.getString("cafe_tel"));
				vo.setImg(rs.getString("cafe_img"));
				vo.setRegion(rs.getString("cafe_region"));
				list.add(vo);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return list;
	}

	// 북마크 확인(중복체크)
	public boolean checkBookmark(int cafeNo, String userId) {
		conn();
		String sql = "SELECT * FROM bookmark " + "WHERE cafe_no=? AND user_id=?";

		try {

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, cafeNo);
			psmt.setString(2, userId);

			int r = psmt.executeUpdate();

			if (r > 0) {
				System.out.println("북마크 " + r + "건 조회");
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return false;
	}

	// 내정보
	public UserVO userOne(String userId) {
		conn();
		String sql = "select * from cfn_user where user_id = ?";
		UserVO vo = new UserVO();

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userId);

			rs = psmt.executeQuery();

			if (rs.next()) {
				vo.setId(rs.getString("user_id"));
				vo.setNickname(rs.getString("user_nick"));
				vo.setTel(rs.getString("user_tel"));
				vo.setImg(rs.getString("user_img"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vo;
	}

	// 회원리스트 수
	public int userCount() {
		conn();
		String sql = "SELECT COUNT(*) as total FROM cfn_user";
		int count = 0;
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			if (rs.next()) {

				count = rs.getInt("total");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return count;
	}

	// 회원리스트 조회(페이징)
	public List<UserVO> userList(int pageNum) {

		int startNum = (pageNum - 1) * 10 + 1;
		int endNum = pageNum * 10;
		conn();
		String sql = "SELECT * FROM (SELECT rownum rn,  a.* FROM (SELECT * FROM cfn_user ORDER BY user_id ) a ) WHERE rn >= ? AND rn <= ?";

		List<UserVO> list = new ArrayList<UserVO>();

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, startNum);
			psmt.setInt(2, endNum);
			rs = psmt.executeQuery();

			while (rs.next()) {

				UserVO vo = new UserVO();

				vo.setId(rs.getString("user_id"));
				vo.setPwd(rs.getString("user_pwd"));
				vo.setNickname(rs.getString("user_nick"));
				vo.setTel(rs.getString("user_tel"));
				vo.setImg(rs.getString("user_img"));

				list.add(vo);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return list;
	}

	// 검색별 회원수
	public int userSearchCount(String category, String keyword) {
		String column = "";

		switch (category) {
		case "id":
			column = "user_id";
			break;
		case "nickname":
			column = "user_nick";
			break;
		case "tel":
			column = "user_tel";
			break;
		}

		conn();
		String sql = "SELECT COUNT(*) as total FROM cfn_user WHERE " + column + " LIKE '%'||?||'%'";
		int count = 0;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, keyword);
			rs = psmt.executeQuery();
			if (rs.next()) {

				count = rs.getInt("total");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return count;
	}

	// 검색 조회
	public List<UserVO> searchUserList(String category, String keyword, int pageNum) {

		int startNum = (pageNum - 1) * 10 + 1;
		int endNum = pageNum * 10;
		String column = "";

		switch (category) {
		case "id":
			column = "user_id";
			break;
		case "nickname":
			column = "user_nick";
			break;
		case "tel":
			column = "user_tel";
			break;
		}

		conn();
		String sql = "select * from (select rownum rn,  a.* from (select * from cfn_user where " + column
				+ " LIKE '%'||?||'%' order by user_id ) a ) where rn >= ? and rn <= ?";

		List<UserVO> list = new ArrayList<UserVO>();
		UserVO vo = null;

		try {

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, keyword);
			psmt.setInt(2, startNum);
			psmt.setInt(3, endNum);
			rs = psmt.executeQuery();

			while (rs.next()) {

				vo = new UserVO();

				vo.setId(rs.getString("user_id"));
				vo.setPwd(rs.getString("user_pwd"));
				vo.setNickname(rs.getString("user_nick"));
				vo.setTel(rs.getString("user_tel"));
				vo.setImg(rs.getString("user_img"));

				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return list;
	}

	// 회원 삭제
	public void deleteUser(String userId) {
		conn();
		String sql = "DELETE FROM cfn_user WHERE user_id=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userId);

			int r = psmt.executeUpdate();
			if (r > 0) {
				System.out.println("회원정보" + r + "건 삭제");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
	}

	// 아이디, 닉네임 중복 확인
	public boolean userInsertcheck(String category, String keyword) {
		String column = "";

		switch (category) {
		case "id":
			column = "user_id";
			break;
		case "nickname":
			column = "user_nick";
			break;
		}

		conn();
		String sql = "SELECT * FROM cfn_user WHERE " + column + " =?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, keyword);

			int r = psmt.executeUpdate();

			if (r > 0) {
				System.out.println("유저 " + r + "건 조회");
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return false;
	}
	
	public boolean checkUser(String id) {
		conn();
		String sql = "SELECT * FROM cfn_user WHERE user_id=?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);

			int r = psmt.executeUpdate();

			if (r > 0) {
				System.out.println("유저 " + r + "건 조회");
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return false;
	}
	
	public boolean kakaoUserInsert(UserVO vo) {

		conn();
		String sql = "INSERT INTO cfn_user(user_id, user_img, user_nick)" 
					+ "VALUES(?,?,?)";

		try {
			
			psmt = conn.prepareStatement(sql);
			
			psmt.setString(1, vo.getId());
			psmt.setString(2, vo.getImg());
			
			if (vo.getNickname()!=null) {
				psmt.setString(3, vo.getNickname());
			} else {
				psmt.setString(3, "null");
			}

			int r = psmt.executeUpdate();
			if (r > 0) {
				System.out.println(r + "건 입력");
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return false;
	}

}
