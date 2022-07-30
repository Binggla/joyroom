package co.dev.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.dev.service.CafeService;
import co.dev.vo.CafeVO;

public class CafeDAO extends DAO_mac implements CafeService {

	// 카페 페이지 갯수
	public int cafeCount() {

		conn();
		String sql = "SELECT COUNT(*) as total FROM cafe";
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

	// 카페 리스트 전체 조회
	public List<CafeVO> cafeList(int pageNum) {

		int startNum = (pageNum - 1) * 10 + 1;
		int endNum = pageNum * 10;
		conn();
		String sql = "SELECT * FROM (SELECT rownum rn,  a.* FROM (SELECT * FROM cafe ORDER BY cafe_no ) a ) WHERE rn >= ? AND rn <= ?";

		List<CafeVO> list = new ArrayList<CafeVO>();

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, startNum);
			psmt.setInt(2, endNum);
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

	// 지역별 카페 페이지 갯수
	public int regionCafeCount(String region) {

		conn();
		String sql = "SELECT COUNT(*) as total FROM cafe WHERE cafe_region = ?";
		int count = 0;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, region);
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

	// 지역별 카페 리스트 조회
	public List<CafeVO> cafeListRegion(String region, int pageNum) {
		int startNum = (pageNum - 1) * 10 + 1;
		int endNum = pageNum * 10;

		conn();
		String sql = "select * from (select rownum rn,  a.* from (select * from cafe where cafe_region = ? order by cafe_no ) a ) where rn >= ? and rn <= ?";
		List<CafeVO> list = new ArrayList<CafeVO>();

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, region);
			psmt.setInt(2, startNum);
			psmt.setInt(3, endNum);
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

	// 검색별 카페 페이지 갯수
	public int searchCafeCount(String keyword) {

		conn();
		String sql = "SELECT COUNT(*) as total FROM cafe WHERE cafe_name LIKE '%'||?||'%'";
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

	// 검색별 카페 리스트 조회
	public List<CafeVO> cafeListSearch(String keyword, int pageNum) {
		int startNum = (pageNum - 1) * 10 + 1;
		int endNum = pageNum * 10;

		conn();
		String sql = "select * from (select rownum rn,  a.* from (select * from cafe where cafe_name LIKE '%'||?||'%' order by cafe_no ) a ) where rn >= ? and rn <= ?";
		List<CafeVO> list = new ArrayList<CafeVO>();

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, keyword);
			psmt.setInt(2, startNum);
			psmt.setInt(3, endNum);
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

	// 카페 1건 조회
	public CafeVO selecCafe(int cafeNo) {

		conn();
		String sql = "SELECT * FROM cafe WHERE cafe_no=?";

		CafeVO vo = null;

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, cafeNo);
			rs = psmt.executeQuery();
			while (rs.next()) {
				vo = new CafeVO();
				vo.setNo(rs.getInt("cafe_no"));
				vo.setName(rs.getString("cafe_name"));
				vo.setAddress(rs.getString("cafe_address"));
				vo.setTel(rs.getString("cafe_tel"));
				vo.setImg(rs.getString("cafe_img"));
				vo.setRegion(rs.getString("cafe_region"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return vo;
	}

	// 카페 등록
	public void insertCafe(CafeVO vo) {

		conn();
		String sql = "INSERT INTO cafe(cafe_no, cafe_name, cafe_address, cafe_tel, cafe_region) "
				+ "VALUES(seq_cafe_no.NEXTVAL,?,?,?,?)";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getName());
			psmt.setString(2, vo.getAddress());
			psmt.setString(3, vo.getTel());
			psmt.setString(4, vo.getRegion());

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

	// 카페 수정
	public void updateCafe(int cafeNo, String newImg) {

		conn();
		String sql = "UPDATE cafe SET cafe_img=? WHERE cafe_no=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, newImg);
			psmt.setInt(2, cafeNo);

			int r = psmt.executeUpdate();
			if (r > 0) {
				System.out.println(r + "건 수정");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}

	}

	// 카페 삭제
	public void deleteCafe(int cafeNo) {

		conn();
		String sql = "DELETE FROM cafe WHERE cafe_no=?";

		try {

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, cafeNo);

			int r = psmt.executeUpdate();
			if (r > 0) {
				System.out.println(r + "건 삭제");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconn();
		}

	}

}
