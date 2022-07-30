package co.dev.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.dev.vo.CafeVO;
import co.dev.vo.adCafeVO;

public class adCafeDAO extends DAO_mac {

	// 메인 광고
	public List<adCafeVO> selectAdCafe() {
			
		conn();
		String sql = "SELECT cafe_no, cafe_name, cafe_img, ad_cafe_info\n"
				+ "FROM cafe\n"
				+ "JOIN (select ad_cafe_no, ad_cafe_info from \n"
				+ "        ( select * from ad_cafe\n"
				+ "          order by DBMS_RANDOM.RANDOM\n"
				+ "     ) where rownum < 3)\n"
				+ "ON cafe_no=ad_cafe_no";
		
		List<adCafeVO> list = new ArrayList<>();
		
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				
				adCafeVO vo = new adCafeVO();
				vo.setNo(rs.getInt("cafe_no"));
				vo.setName(rs.getString("cafe_name"));
				vo.setImg(rs.getString("cafe_img"));
				vo.setInfo(rs.getString("ad_cafe_info"));
				
				list.add(vo);
				}
			
			int r = psmt.executeUpdate();
			
			if(r > 0) {
				System.out.println("광고 " + r + "건 조회");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 관리자 페이지 광고 리스트
	public List<adCafeVO> selectAdList() {
		conn();
		String sql = "SELECT cafe_no, cafe_name, cafe_img, ad_cafe_info\n"
				+ "FROM cafe\n"
				+ "JOIN (select ad_cafe_no, ad_cafe_info from ad_cafe )\n"
				+ "ON cafe_no=ad_cafe_no";
		
		List<adCafeVO> list = new ArrayList<>();
		
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				
				adCafeVO vo = new adCafeVO();
				vo.setNo(rs.getInt("cafe_no"));
				vo.setName(rs.getString("cafe_name"));
				vo.setImg(rs.getString("cafe_img"));
				vo.setInfo(rs.getString("ad_cafe_info"));
				
				list.add(vo);
				}
			
			int r = psmt.executeUpdate();
			
			if(r > 0) {
				System.out.println("광고 " + r + "건 조회");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 광고 등록 페이지에서 카페 전체 리스트 조회
	public List<CafeVO> selectCafeList() {
		
		conn();
		String sql = "SELECT cafe_no, cafe_name FROM CAFE\n"
				+ "WHERE cafe_no IN (\n"
				+ "		SELECT cafe_no\n"
				+ "		FROM cafe\n"
				+ "		MINUS\n"
				+ "		SELECT ad_cafe_no\n"
				+ "		FROM ad_cafe)";
		
		List<CafeVO> list = new ArrayList<>();
		
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				
				CafeVO vo = new CafeVO();
				vo.setNo(rs.getInt("cafe_no"));
				vo.setName(rs.getString("cafe_name"));
				
				list.add(vo);
				}
			
			int r = psmt.executeUpdate();
			
			if(r > 0) {
				System.out.println("카페 " + r + "건 조회");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
		
	}

	// 광고 등록
	public boolean insertAdCafe(String adCafeNo, String adCafeInfo) {
		
		conn();
		String sql = "INSERT INTO ad_cafe "
				+ "VALUES (?,?)";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, adCafeNo);
			psmt.setString(2, adCafeInfo);
			
			int r = psmt.executeUpdate();
			
			if(r > 0) {
				System.out.println("광고 " + r + "건 등록");
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
			
	}

	public boolean deleteAdCafe(String adCafeNo) {
		
		conn();
		String sql = "DELETE ad_cafe "
				+ "WHERE ad_cafe_no=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, adCafeNo);
			
			int r = psmt.executeUpdate();
			
			if(r > 0) {
				System.out.println("광고 " + r + "건 삭제");
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	// 광고 수정
	public boolean updateAdCafe(String adCafeNo, String adCafeInfo) {
		conn();
		String sql = "UPDATE ad_cafe "
				+ "SET ad_cafe_info=? "
				+ "WHERE ad_cafe_no=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, adCafeInfo);
			psmt.setString(2, adCafeNo);
			
			int r = psmt.executeUpdate();
			
			if(r > 0) {
				System.out.println("광고 " + r + "건 수정");
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
