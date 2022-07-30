package co.dev.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.dev.service.boardPageService;
import co.dev.vo.BoardVO;


public class boardDAO extends DAO_mac implements boardPageService {

	// 게시판 페이지 갯수
	public int Count() {

		conn();
		String sql = "SELECT COUNT(*) as total FROM board";
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

	// 글쓰기
	public void insertBoard(BoardVO board) {
		conn();
		String sql = "insert into board values(seq_board.nextval,?, ?, ?, sysdate, ?, ?)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, board.getTitle());
			psmt.setString(2, board.getUser_id());
			psmt.setString(3, board.getContent());
			psmt.setString(4, board.getImg());
			psmt.setInt(5, 0);

			int r = psmt.executeUpdate();
			System.out.println(r + "건 입력됨.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
	}

	// 게시글 수정
	public void updateBoard(BoardVO board) {
		conn();
		String sql = "update board set board_title=?, board_content=? where board_no=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, board.getTitle());
			psmt.setString(2, board.getContent());
			psmt.setInt(3, board.getNo());
			psmt.executeUpdate();
			System.out.println(board.getNo() + "게시글의 내용이 수정되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
	}

	// 게시글 삭제
	public void deleteBoard(int num) {
		conn();
		String sql = "delete from board where board_no=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, num);
			psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
	}

	// 게시글 수정
	public void updateBoard(int board_no, String board_title, String board_content) {
		conn();
		String sql = "update board set board_title=?, board_content=? where board_no=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, board_title);
			psmt.setString(2, board_content);
			psmt.setInt(3, board_no);
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
	}

	// 내글 조회
	public List<BoardVO> myBoard() {
		conn();

		List<BoardVO> list = new ArrayList<BoardVO>();

		try {
			psmt = conn.prepareStatement("select * from board where user_id=?");
			rs = psmt.executeQuery();
			while (rs.next()) {
				BoardVO vo = new BoardVO();
				vo.setTitle(rs.getString("board_title"));
				vo.setDate(rs.getString("board_date"));
				vo.setView(rs.getInt("board_view"));
				vo.setImg(rs.getString("board_img"));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return list;
	}

	// 게시판 조회
	public List<BoardVO> listBoard() {
		conn();

		List<BoardVO> list = new ArrayList<BoardVO>();

		try {
			psmt = conn.prepareStatement("select * from board order by board_no desc");
			rs = psmt.executeQuery();
			while (rs.next()) {
				BoardVO vo = new BoardVO();
				vo.setNo(rs.getInt("board_no"));
				vo.setTitle(rs.getString("board_title"));
				vo.setUser_id(rs.getString("user_id"));
				vo.setContent(rs.getString("board_content"));
				vo.setDate(rs.getString("board_date"));
				vo.setImg(rs.getString("board_img"));
				vo.setView(rs.getInt("board_view"));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return list;
	}

	// 상세 보기
	public BoardVO oneBoard(int num) {
		conn();

		try {
			psmt = conn.prepareStatement("select * from board where board_no = ?");
			psmt.setInt(1, num);
			rs = psmt.executeQuery();
			if (rs.next()) {
				BoardVO vo = new BoardVO();
				vo.setNo(rs.getInt("board_no"));
				vo.setTitle(rs.getString("board_title"));
				vo.setUser_id(rs.getString("user_id"));
				vo.setContent(rs.getString("board_content"));
				vo.setDate(rs.getString("board_date"));
				vo.setImg(rs.getString("board_img"));
				vo.setView(rs.getInt("board_view"));
				return vo;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return null;
	}

	// 조회수 증가
	public BoardVO plusView(int num) {
		conn();

		try {
			psmt = conn.prepareStatement("update board set board_view = board_view+1 where board_no = ?");
			psmt.setInt(1, num);
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return null;
	}

	// 게시글 마지막번호 찾기
	public int findIndex() {
		conn();
		int lastIx = 1; // 커뮤니티 첫번째 게시글이면 게시글번호가 1임
		try {
			psmt = conn.prepareStatement("select board_no from board where rownum=1 order by board_no desc");
			rs = psmt.executeQuery();

			while (rs.next()) {
				lastIx = rs.getInt("board_no");
			}
			System.out.println("last Index : " + lastIx);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}

		return lastIx;
	}
	// 게시글 전체 조회
	public List<BoardVO> listMyBoard(String userId) {
		conn();
		
		List<BoardVO> list = new ArrayList<BoardVO>();

		try {
			psmt = conn.prepareStatement("select * from board where user_id = ? order by board_no desc");
			psmt.setString(1, userId);
			rs = psmt.executeQuery();
			while (rs.next()) {
				BoardVO vo = new BoardVO();
				vo.setNo(rs.getInt("board_no"));
				vo.setTitle(rs.getString("board_title"));
				vo.setUser_id(rs.getString("user_id"));
				vo.setContent(rs.getString("board_content"));
				vo.setDate(rs.getString("board_date"));
				vo.setImg(rs.getString("board_img"));
				vo.setView(rs.getInt("board_view"));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return list;
	}

	
	//페이지 짤라버리기
	@Override
	public List<BoardVO> boardList(int pageNum) {
		conn();
		int startNum = (pageNum-1)*10+1;
		int endNum = pageNum*10;
		String sql = "SELECT * FROM (SELECT rownum rn,  a.* FROM (SELECT * FROM board ORDER BY board_no desc) a ) WHERE rn >= ? AND rn <= ?";
		
		List<BoardVO> list = new ArrayList<BoardVO>();
		try {
			psmt=conn.prepareStatement(sql);
			psmt.setInt(1, startNum);
			psmt.setInt(2, endNum);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				
				BoardVO vo = new BoardVO();
				
				vo.setNo(rs.getInt("board_no"));
				vo.setTitle(rs.getString("board_title"));
				vo.setUser_id(rs.getString("user_id"));
				vo.setContent(rs.getString("board_content"));
				vo.setDate(rs.getString("board_date"));
				vo.setImg(rs.getString("board_img"));
				vo.setView(rs.getInt("board_view"));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return list;
	}

	// 게시글 번호 부여 메소드
//	public int getNext() {
//		//현재 게시글을 내림차순으로 조회하여 가장 마지막 글의 번호를 구한다
//		String sql = "select board_no from board order by board_no desc";
//		try {
//			psmt=conn.prepareStatement(sql);
//			rs= psmt.executeQuery();
//			if(rs.next()) {
//				return rs.getInt(1)+1;
//			}
//			return 1; // 첫 번째 게시물인 경우
//		} catch (SQLException e) {
//			// 
//			e.printStackTrace();
//		}
//		return -1; //데이터베이스 오류
//	}
//	

}
