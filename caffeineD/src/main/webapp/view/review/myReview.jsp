<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>myReview.jsp</title>
	<link href="${pageContext.servletContext.contextPath }/css/review.css"
		rel="stylesheet">
		<link href="${pageContext.servletContext.contextPath }/css/myReview.css"
		rel="stylesheet">
	<style>
		#my_review {
			background: #d9bba9;
			color: #2F170F;
			font-weight: 600;
		}
		.admin_menu {
			display: inline-block;
			width: 1110px;
		}
	</style>
</head>
<body>

	<c:choose>
		<c:when test="${!empty error}">
			<script>
				alert("${error }")
			</script>
			<%
			request.getSession().removeAttribute("error");
			%>
		</c:when>
		<c:otherwise>
			<c:if test="${!empty success }">
				<script>
					alert("${success }")
				</script>
				<%
				request.getSession().removeAttribute("success");
				%>
			</c:if>
		</c:otherwise>
	</c:choose>

	<div id="container" class="text_center">
	<div class="admin_menu">
		<div class="heading-section" style="z-index:-1;"><jsp:include page="/view/myPage/myPageMenu.jsp"></jsp:include></div>
	</div>
	<div class="review_guide text_center">
		<p>내 리뷰는 날짜 순으로 나열되며<br>
		리뷰 정책 위반 시, 관리자에 의해 리뷰가 삭제될 수 있습니다.</p>
		<div>
			<button class="mini_button button_no_back review_guide_btn" type="button"
									onclick="reviewGuidePop();">관련 정책 보기</button>
			<p id="review_guide_content" style="display:none;"> - 특정인의 명예를 훼손하거나 저작권을 침해한 경우<br>
				- 개인정보를 포함한 경우 (연락처, 이메일, SNS, 주소 등)<br>
				- 상업적 목적이 담긴 경우<br>
				- 식품 등의 표시광고 법률에 따라, 제한되는 내용이 포함된 경우<br>
				- 다른 카페 리뷰에 동일한 내용을 반복 기재한 경우<br>
				- 단순 문의사항이나 상품과 상관없는 텍스트, 사진 첨부<br>
				- 다른 사람에게 위화감을 주는 경우
			</p>
		</div>
	</div>
		<c:choose>
			<c:when test="${empty myReviewList }">리뷰가 없습니다.</c:when>
			<c:otherwise>
				<article>

					<c:forEach var="review" items="${myReviewList }">
						<div class="review_section">
							<div id="${review.no }" class="review">
							<div class="cafe_info">
								<div class="cafe_img"><img src="img/cafeimg/${review.cafeImg }"></img></div>
								<div class="cafe_text">
									<p><strong>${review.cafeName }</strong>에 작성된 리뷰입니다.</p>
									<button type="button" class="button_no_back mini_button"
										onclick="location.href='${pageContext.servletContext.contextPath }/cafeInfoSelect.do?no=${review.cafeNo}'">카페
										바로가기</button>
								</div>
								
							</div>

								<div class="review_left">
									<c:if test="${!empty review.img }">
										<div class="review_img"><img width="100%"
											src="${pageContext.servletContext.contextPath }/img/reviewimg/${review.img }"></div>
									</c:if>
								</div>
								<div class="review_right text_left">
									<div class="review_user_prof">
										<input type="hidden" value="${user.id }" name="loginUser">
										
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
									<div>
										<span class="star" style=""> <c:choose>
												<c:when test="${review.star eq '5' }">★★★★★</c:when>
												<c:when test="${review.star eq '4' }">★★★★☆</c:when>
												<c:when test="${review.star eq '3' }">★★★☆☆</c:when>
												<c:when test="${review.star eq '2' }">★★☆☆☆</c:when>
												<c:when test="${review.star eq '1' }">★☆☆☆☆</c:when>
											</c:choose>
										</span> <span class="like_section"> <span
											id="likeCount_${review.no }">${review.like }</span>
											<c:choose>
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
									<div class="review_content">${review.content }</div>

								</div>

								<div class="review_date">${review.date }</div>

							</div>
							<div class="btn_section text_right">
								<a href="#reviewUpdate" class="btn_open"><button
										type="button" class="mini_button button_no_back cursor_pointer"
										onclick="javascript:topBtnNone();">수정</button></a> <a href="#"><button
										class="delBtn mini_button button_no_back cursor_pointer" name="delBtn_${review.no }" type="button"
										>삭제</button></a>
							</div>
						</div>
					</c:forEach>
				<button class="button_no_back" id="moreBtn" onClick="listMore()">
					<img
						src="${pageContext.servletContext.contextPath }/img/moreBtn.png"
						style="width: 50px;">
				</button>
				</article>
				<div id="topBtn" class="cursor_pointer"
					onclick="window.scrollTo(0,0);">TOP</div>
			</c:otherwise>
		</c:choose>

		<div class="wrap">
			<!-- 리뷰 수정 모달 -->
			<div id="reviewUpdate" class="pop_wrap">
				<!-- 모달 닫기 -->
				<div class="pop_inner">
					<button type="button" id="btn_close" class="button_no_back">X</button>
					<br>
					<!-- 수정 폼 -->
					<form
						action="${pageContext.servletContext.contextPath }/reviewUpdate.do"
						method="post" enctype="multipart/form-data"
						onsubmit="return submitCheck();">
						
						<!-- 등록 폼 왼쪽 섹션 -->
						<div class="pop_left">
							<input type="hidden" id="reviewNo" name="reviewNo">
							
							<!-- 사진 업로드, 미리보기 -->
							<div id="imgSection">
								<div id="reviewImgDiv">
									<img id="firstImg"
										src="${pageContext.servletContext.contextPath }/img/emptyimg.jpg">
								</div>
							</div>
			
							<label for="imgFile">사진 올리기</label> 
							<input id="imgFile" name="img" type="file" 
									accept="image/jpg, image/png, image/jpeg">	
						</div>
						
						<!-- 등록 폼 오른쪽 섹션 -->
						<div class="pop_right">
						
							<!-- 평점 버튼 -->
							<div id="insertStar">
								<input id="starVal" type="hidden" name="star" value="">
								<c:forEach var="i" begin="1" end="5">
									<button class="starBtn button_no_back" type="button" name="${i }">
										<img src="${pageContext.servletContext.contextPath }/img/eptstar.svg.png">
									</button>
								</c:forEach>
							</div>
							
							<!-- 리뷰 내용 -->
							<textarea id="rContent" name="content"
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
	</div>

</body>
<script src="js/myReview.js"></script>
<script>
	const firstCount = 3;
	const moreCount = 5;
	let showCount = firstCount;
	
	let cafes = document.getElementsByClassName('cafe_info');
	
	let reviews = document.getElementsByClassName('review_section');
	let moreBtn = document.getElementById('moreBtn');
	let topBtn = document.querySelector('#topBtn');
	if (reviews.length <= 3) {
		moreBtn.style.display = 'none';
		topBtn.style.display = 'none';

	} else {
		for (let i = firstCount; i < reviews.length; i++) {
			reviews[i].style.display = 'none';
			cafes[i].style.display = 'none';
		}
	}

	function listMore() {
		console.log('more');
		for (let i = showCount; i < showCount + moreCount; i++) {
			if (reviews[i] != null) {
				cafes[i].style.display = 'block';
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
	
	function reviewGuidePop() {
		let guideText = document.querySelector('#review_guide_content');
		if (guideText.style.display === 'none') {
			console.log('guide open');
			guideText.style.display = 'block';
		} else if (guideText.style.display === 'block') {
			console.log('guide close');
			guideText.style.display = 'none';
		}
	}
</script>
</html>