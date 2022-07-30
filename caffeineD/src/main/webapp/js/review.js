// 리뷰 인포(평점) 함수 실행
StarAvg();

// 좋아요 버튼 이벤트
let likeBtns = document.querySelectorAll('.likeBtn');
for (let i = 0; i < likeBtns.length; i++) {
	likeBtns[i].addEventListener('click', likeCallBack);
}


// 평점 버튼 이벤트
function starValue(btn) {
	let starBtns = document.querySelectorAll('.starBtn');

	for (let i = 0; i < 5; i++) {
		let starImg = starBtns[i].childNodes[1];
		starImg.src = getPathRootJump() + 'img/eptstar.svg.png';
	}

	for (let i = 0; i < Number(btn.name); i++) {
		let starImg = starBtns[i].childNodes[1];
		starImg.src = getPathRootJump() + 'img/star.svg.png';
	}

	btn.parentNode.childNodes[1].value = btn.name;

} // end of starCallBack()


// 등록 모달창 열기
function modalOpen(btn) {
	let targetID = btn.id;
	document.querySelector(targetID).style.display = 'block';
}

// 등록 모달창 닫기
function modalClose(btn) {
	btn.parentNode.parentNode.style.display = 'none';
	let frm = document.forms.insertForm;
	
	// 입력된 내용 모두 초기화
	frm.reset();
	
	// 색칠된 별 초기화
	let starBtns = document.querySelectorAll('.starBtn');
	for (let i = 0; i < 5; i++) {
		let starImg = starBtns[i].childNodes[0];
		starImg.setAttribute('src', getPathRootJump() + 'img/eptstar.svg.png');
	}
	
	// 사진 미리보기 초기화
	let reviewImg = document.querySelector('#reviewImg');
	let uploadImg = document.querySelector('#uploadImg');
	let imgDelBtn = document.querySelector('#imgDelBtn');
	if (reviewImg) {
		reviewImg.setAttribute('id', 'firstImg');
		reviewImg.setAttribute('src', getPathRootJump() + `img/emptyimg.jpg`);
		imgDelBtn.remove();
		uploadImg.value = '';
	}

	let topBtn = document.querySelector('#topBtn');
	topBtn.style.display = 'block';
	
}

// 사진 미리보기 수정, 삭제
let imgFile = document.querySelector('#imgFile');
imgFile.addEventListener('change', (changeEvent) => {			

	let imgSection = document.querySelector('#imgSection');		// 이미지 전체 div
	let reviewImgDiv = document.querySelector('#reviewImgDiv');	// div 이미지 + 삭제 버튼
	//let reviewImg = document.querySelector('#reviewImg');		// img
	//let imgFile = document.querySelector('#imgFile');			// input[type=file]

																//<div #imgSection> 
																//	<div #reviewImgDiv>
																//		<img #reviewImg>
																//		<input type="button">
																//	</div>
																//	<input type="file" #imgFile>
																//</div>	

	const reader = new FileReader();
	reader.addEventListener('load', function(readerEvent) {

		if (reviewImgDiv) {			// 이미 존재하는 이미지가 있다면(first or review) 삭제.
			reviewImgDiv.remove();
		}

		let div = document.createElement('div');	// 위의 html 구조로 new reviewImgDiv를 생성.
		div.setAttribute('id', 'reviewImgDiv')

		let img = document.createElement('img');
		img.setAttribute('id', 'reviewImg')
		img.setAttribute('src', readerEvent.target.result);

		div.appendChild(img)			
		div.appendChild(makeX());		
		imgSection.appendChild(div);	

	});

	const img = changeEvent.target.files[0];
	reader.readAsDataURL(img);

})

// 업로드된 이미지 삭제 버튼
function makeX() {
	
	let chk_style = 'width:25px; height:25px; position:absolute; font-size:15px;' +
	'right:0px; bottom:0px; z-index:999; background-color:rgba(0,0,0,0.5); color:white; border:none;';
	
	let btn = document.createElement('input');
	btn.setAttribute('type', 'button');
	btn.setAttribute('value', 'x');
	btn.setAttribute('id', 'imgDelBtn');
	btn.setAttribute('style', chk_style);

	btn.addEventListener('click', function() {
		
		// 업로드된 이미지를 삭제하면서 기본 이미지로 변경.
		let reviewImg = document.querySelector('#reviewImg');
		let imgFile = document.querySelector('#imgFile');

		reviewImg.setAttribute('id', 'firstImg');
		reviewImg.setAttribute('src', getPathRootJump() + `/img/emptyimg.jpg`);
		this.remove();
		imgFile.value = '';

	})

	return btn;
}





// function
// 평균 평점 조회
function StarAvg() {

	let cafeNo = document.getElementsByName('cafeNo')[0].value;

	fetch('starAvg.do', {
		method: 'post',
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
		body: `cafeNo=${cafeNo}`
	})
		.then(result => result.json())
		.then(reviewInfo => {
			// 리뷰 평균
			let starAvg = document.querySelector('#star_avg');
			let text = document.createTextNode(reviewInfo.starAvg);
			starAvg.appendChild(text);

			// 평균으로 별 출력
			let starShow = document.querySelector('#star_show');
			if (Number(reviewInfo.starAvg) > 4) {
				text = document.createTextNode('★★★★★');
			} else if (Number(reviewInfo.starAvg) > 3) {
				text = document.createTextNode('★★★★☆');
			} else if (Number(reviewInfo.starAvg) > 2) {
				text = document.createTextNode('★★★☆☆');
			} else if (Number(reviewInfo.starAvg) > 1) {
				text = document.createTextNode('★★☆☆☆');
			} else {
				text = document.createTextNode('★☆☆☆☆');
			}
			starShow.appendChild(text);

			// 리뷰 
			let reviewCount = document.querySelector('#review_count');
			text = document.createTextNode('리뷰 ' + reviewInfo.reviewCount);
			reviewCount.appendChild(text);

			// 점수에 따른 평점 개수
			let trs = document.querySelectorAll('tr');

			// 반복문 돌리고 싶따...
			let starCountAry = [reviewInfo.starCount_1, reviewInfo.starCount_2, reviewInfo.starCount_3, 
								reviewInfo.starCount_4, reviewInfo.starCount_5]
			
			for (let i=0; i<5; i++) {
				let td = document.createElement('td');
				text = document.createTextNode(starCountAry[i]);
				td.appendChild(text);
				trs[i].appendChild(td);
			}

			
			let graphs = document.querySelectorAll('.star_count_show_color');
			reviewCount = Number(reviewInfo.reviewCount);

			starCountAry.forEach(function (val, idx) {
				graphs[idx].setAttribute('style', `width: ${120 * (Number(val) / reviewCount)}px;`);
			});
		


		})
		.catch(error => console.log(error));
}

// 좋아요 버튼 콜백
function likeCallBack() {
	// 클릭된 버튼의 id값으로 리뷰 번호 받아오기
	let reviewNo = this.id.substr(8);

	// 받아온 리뷰 번호로 버튼, 하트 사진, 좋아요 수 변수 지정.
	let likeBtn = document.getElementById(`likeBtn_${reviewNo}`);
	let likeHeart = document.getElementById(`heart_${reviewNo}`);
	let likeCount = document.getElementById(`likeCount_${reviewNo}`);

	if (this.name === 'unlike') {
		// 좋아요가 눌려있지 않을 때
		// 버튼을 누르면 해당 리뷰의 전체 좋아요 수 +1
		// 현재 유저의 해당 리뷰에 대한 좋아요 정보 추가
		
		// Front : 좋아요 상태 변경, 채워진 하트로 사진 변경, 좋아요 수 +1
		likeBtn.setAttribute('name', 'like');
		likeHeart.setAttribute('src', getPathRootJump() + 'img/heart.svg.png');
		likeCount.innerHTML = String(Number(likeCount.innerHTML) + 1);
		
		// Back : 리뷰 번호 넘겨서 서버 작업
		reviewLike('like', `${reviewNo}`);

	} else if (this.name === 'like') {
		// 좋아요가 눌려있을 때
		// 버튼을 누르면 전체 좋아요 수 -1
		// 현재 유저의 해당 리뷰에 대한 좋아요 정보 삭제

		// Front : 좋아요 상태 변경, 빈 하트로 사진 변경, 좋아요 수 -1
		likeBtn.setAttribute('name', 'unlike');
		likeHeart.setAttribute('src', getPathRootJump() + 'img/eptheart.svg.png')
		likeCount.innerHTML = String(Number(likeCount.innerHTML) - 1);
		
		// Back : 리뷰 번호 넘겨서 서버 작업
		reviewLike('unlike', `${reviewNo}`);


	} else if (this.name === 'nonUser') {
		// 로그인 유저가 없을 경우 
		console.log('nonuser');
		alert('로그인이 필요합니다.');

	}

} //end of likeCallBack()

function reviewLike(state, no) {
			fetch('reviewLike.do', {
				method: 'post',
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
				body: `job=${state}&reviewNo=${no}`
			})
				.catch(error => console.log(error));
}



// 절대 경로
function getPathRootJump() {
	var pathName = window.location.pathname.substring(1);
	var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
	var path_root = window.location.protocol + '//' + window.location.host + '/' + webName + '/';
	return path_root;
}