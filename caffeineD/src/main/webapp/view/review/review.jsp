<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>review.jsp</title>
	</head>
	<link href="${pageContext.servletContext.contextPath }/css/review.css"
		rel="stylesheet">
	<link href="${pageContext.servletContext.contextPath }/css/avgStar.css"
		rel="stylesheet">
<body class="text_center">

	<div id="container" class="text_center">
		
		<!-- 리뷰 평점 인포메이션 -->
		<article id="reviews_info">
			<c:choose>
				<c:when test="${!empty reviewList }">
					
					<div>
						<span id="star_avg"></span><span id="total_star"> / 5</span>
					</div>
					<div id="star_show"></div>
					<div id="review_count"></div>
	
					<table>
						<c:forEach var="i" begin="1" end="5">
							<tr>
								<td><c:out value="${i }" /></td>
								<td class="star_info">★</td>
								<td>
									<div class="star_count_show">
										<div class="star_count_show_color"></div>
									</div>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				
				<c:otherwise>
					<p>등록된 리뷰가 없습니다.</p>
				</c:otherwise>
			
			</c:choose>
		</article>

		<article>
			<!-- 리뷰 작성 버튼, 내 리뷰 안내 -->
			<c:if test="${!empty user }">
				<div>
					<button id="#reviewInsert" class="review_btn"
						onclick="topBtnNone(); modalOpen(this);">Write</button>
				</div>
				<div class="review text_center">
					<p>리뷰는 좋아요 순으로 나열되며<br>
					리뷰 수정, 삭제는 마이페이지의 내 리뷰에서 가능합니다.</p>
					<button class="mini_button button_no_back" type="button"
						onclick="location.href='${pageContext.servletContext.contextPath }/myReview.do'">내 리뷰 바로가기</button>
				</div>
			</c:if>
				

			<!-- 리뷰 리스트 -->
			<c:forEach var="review" items="${reviewList }">
				<div id="${review.no }" class="review">
				
					<!-- 리뷰 왼쪽 섹션 -->
					<div class="review_left">
					
						<!-- 리뷰 사진 -->
						<c:if test="${!empty review.img }">
							<div class="review_img">
								<img width="100%"
									src="${pageContext.servletContext.contextPath }/img/reviewimg/${review.img }">
							</div>
						</c:if>
						
					</div>
					
					<!-- 리뷰 오른쪽 섹션 -->
					<div class="review_right text_left">
					
						<!-- 리뷰 유저 프로필 -->
						<div class="review_user_prof">
							<input type="hidden" value="${user.id }" name="login_user"> 
							
							<span class="userProf">
								<c:set var="id" value="${review.userId }" />
								<c:choose>
									<c:when test="${fn:substring(id,0,5) == 'kakao' }">
											<img src="${review.userImg }">
									</c:when>
									<c:otherwise>
											<img src="${pageContext.servletContext.contextPath }/img/profimg/${review.userImg }">
									</c:otherwise>
								</c:choose>
							</span>
							<span class="review_user_nick bold_text">${review.userNick }</span>
						</div>
						
						<!-- 리뷰 평점, 좋아요 -->
						<div>
							<span class="star" style=""> 
								<c:choose>
									<c:when test="${review.star eq '5' }">★★★★★</c:when>
									<c:when test="${review.star eq '4' }">★★★★☆</c:when>
									<c:when test="${review.star eq '3' }">★★★☆☆</c:when>
									<c:when test="${review.star eq '2' }">★★☆☆☆</c:when>
									<c:when test="${review.star eq '1' }">★☆☆☆☆</c:when>
								</c:choose>
							</span> 
							
							<span class="like_section"> 
								<span id="likeCount_${review.no }">${review.like }</span>
									<c:choose>
										<c:when test="${empty user }">
											<button id="likeBtn_${review.no }" class="likeBtn"
												type="button" name="nonUser">
												<img id="heart_${review.no }"
													src="${pageContext.servletContext.contextPath }/img/eptheart.svg.png">
											</button>
										</c:when>
										<c:when test="${review.likeCheck == 'true' }">
											<button id="likeBtn_${review.no }" class="likeBtn"
												type="button" name="like">
												<img id="heart_${review.no }"
													src="${pageContext.servletContext.contextPath }/img/heart.svg.png">
											</button>
										</c:when>
										<c:otherwise>
											<button id="likeBtn_${review.no }" class="likeBtn"
												type="button" name="unlike">
												<img id="heart_${review.no }"
													src="${pageContext.servletContext.contextPath }/img/eptheart.svg.png">
											</button>
										</c:otherwise>
									</c:choose>
								</span>
							
						</div>
						
						<!-- 리뷰 내용 -->
						<div class="review_content">${review.content }</div>

					</div>
					
					<!-- 리뷰 작성 날짜 -->
					<div class="review_date">${review.date }</div>
				</div>
			</c:forEach>
		</article>
		
		<!-- 더보기 -->
		<button class="button_no_back" id="moreBtn" onClick="listMore()">
			<img src="${pageContext.servletContext.contextPath }/img/moreBtn.png"
				style="width: 50px;">
		</button>
		
		<!-- TOP -->
		<div id="topBtn" class="cursor_pointer"
			onclick="window.scrollTo(0,0);">TOP</div>
			
	</div> <!-- end of #container -->

	<!-- 리뷰 등록 팝업 -->
	<div class="wrap">
		<div id="reviewInsert" class="pop_wrap">
			<div class="pop_inner">
				<!-- 팝업 닫기 -->
				<button type="button" class="button_no_back" onclick="modalClose(this)">X</button>
				
				<!-- 등록 폼 -->
				<form name="insertForm"
					action="${pageContext.servletContext.contextPath }/reviewInsert.do"
					method="post" enctype="multipart/form-data"
					onsubmit="return submitCheck();">
					
					<!-- 등록 폼 왼쪽 섹션 -->
					<div class="pop_left">
						<input type="hidden" name="cafeNo" value="${cafeinfo.no }">
						
						<!-- 사진 업로드, 미리보기 -->
						<div id="imgSection">
							<div id="reviewImgDiv">
								<img id="firstImg"
									src="${pageContext.servletContext.contextPath }/img/emptyimg.jpg">
							</div>
						</div>
			
						<label for="imgFile">사진 올리기</label> <input id="imgFile"
							name="img" type="file" accept="image/jpg, image/png, image/jpeg">	
					</div>
					
					<!-- 등록 폼 오른쪽 섹션 -->
					<div class="pop_right">
						
						<!-- 평점 버튼 -->
						<div id="insertStar">
							<input id="starVal" type="hidden" name="star" value="">
							<c:forEach var="i" begin="1" end="5">
								<button class="starBtn button_no_back" type="button" name="${i }" onclick="starValue(this)">
									<img src="${pageContext.servletContext.contextPath }/img/eptstar.svg.png">
								</button>
							</c:forEach>
						</div>
						
						<!-- 리뷰 내용 -->
						<textarea name="content"
							placeholder="리뷰는 10자 이상 입력해 주세요.&#13;&#10;5MB 이하 PNG, JPG, JPEG 형식의&#13;&#10;파일 1개만 등록 가능합니다."
							cols="30" rows="9" minlength="10" required
							oninvalid="this.setCustomValidity('리뷰를 10자 이상 입력해주세요.')"></textarea>
							
						<!-- 업로드 버튼 -->
						<div style="text-align: center;">
							<button class="review_btn" type="submit">Upload</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>



<script src="js/review.js"></script>
<script>
	const firstCount = 3;
	const moreCount = 5;
	let showCount = firstCount;

	let reviews = document.getElementsByClassName('review');
	let moreBtn = document.getElementById('moreBtn');

	if (reviews.length <= 3 ) {
		moreBtn.style.display = 'none';
	} else {
		for (let i = firstCount; i < reviews.length; i++) {
			reviews[i].style.display = 'none';
		}
	}

	function listMore() {
		for (let i = showCount; i < showCount + moreCount; i++) {
			if (reviews[i] != null) {
				reviews[i].style.display = 'block';
			} else {
				moreBtn.style.display = 'none';
			}
		}
		showCount += moreCount;
	}
	
	function submitCheck() {
		let starVal = document.querySelector('#starVal');
		if (starVal.value === '') {
			alert('평점을 선택해주세요.')
			return false;
		}
	}

	function topBtnNone() {
		let topBtn = document.querySelector('#topBtn');
		topBtn.style.display = 'none';
	}
</script>
</body>
</html>