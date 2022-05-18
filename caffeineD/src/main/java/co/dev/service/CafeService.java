package co.dev.service;

import java.util.List;

import co.dev.vo.CafeVO;

public interface CafeService {
	
	// 카페 리스트 전체 조회
	public List<CafeVO> cafeList();
	// 지역별 카페 리스트 조회
	public List<CafeVO> cafeListRegion(String region);
	// 카페 1건 조회
	public CafeVO selecCafe(int cafeNo);
	// 카페 등록
	public void insertCafe(CafeVO vo);
	// 카페 수정
	public void updateCafe(CafeVO vo);
	// 카페 삭제
	public void deleteCafe(int cafeNo);
	
}