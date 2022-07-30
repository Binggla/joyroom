// 수정 팝업
let target = document.querySelectorAll('.btn_open');
let btnPopClose = document.querySelector('#btn_close');
// 팝업 열기
for(let i=0; i<target.length; i++) {
	target[i].addEventListener('click', openPopCallBack);
}
// 팝업 닫기
btnPopClose.addEventListener('click', closePopCallBack);

	

// 좋아요 버튼
let likeBtns = document.querySelectorAll('.likeBtn');

for (let i = 0; i < likeBtns.length; i++) {
	likeBtns[i].addEventListener('click', likeCallBack);
}


// 삭제 팝업
let delBtns = document.querySelectorAll('.delBtn');
for (let i = 0; i < delBtns.length; i++) {
	delBtns[i].addEventListener('click', function() {
		let result = confirm('정말로 삭제하시겠습니까?');
		if (result) {
			console.log("yes")
			fetch(getPathRootJump() + 'reviewDelete.do', {
				method: 'post',
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
				body: `job=like&review_no=${this.name.substr(7)}`
			})
				.then(result => result.json())
				.then(result => {
					alert(`${result.messege}`);
					location.reload();
				})
				.catch(error => console.log(error));
		}
	});
}


// 평점
let starBtns = document.querySelectorAll('.starBtn');

for (let i = 0; i < starBtns.length; i++) {
	starBtns[i].addEventListener('click', starCallBack);
}


// 사진 미리보기 수정, 삭제
let imgFile = document.querySelector('#imgFile');

// 이미지 미리보기
imgFile.addEventListener('change', function(changeEvent) {
	
	let imgSection = document.querySelector('#imgSection');		
	let reviewImgDiv = document.querySelector('#reviewImgDiv');	
	
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

// 평점 콜백
function starCallBack() {
	let starBtns = document.querySelectorAll('.starBtn');

	for (let i = 0; i < 5; i++) {
		let starImg = starBtns[i].childNodes[1];
		starImg.setAttribute('src', getPathRootJump() + '/img/eptstar.svg.png');
	}

	for (let i = 0; i < Number(this.name); i++) {

		let starImg = starBtns[i].childNodes[1];
		starImg.setAttribute('src', getPathRootJump() + '/img/star.svg.png');
	}

	document.querySelector('#starVal').value = this.name;

} //end of starCallBack()


// 팝업 열기 콜백
function openPopCallBack() {

	let targetID = this.getAttribute('href');
	let popDiv = document.querySelector(targetID);
	popDiv.style.display = 'block';
	
	let reviewNo = this.parentNode.childNodes[3].childNodes[0].name.substr(7);

	fetch(getPathRootJump() + 'reviewSelect.do', {
		method: 'post',
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
		body: `reviewNo=${reviewNo}`
	})
		.then(result => result.json())
		.then(review => {

			// 이미지	불러오기
			if (review.img != null) {
				
				var reviewImgDiv = document.querySelector('#reviewImgDiv');	// 이미지가 보여지는 섹션 div	
				var firstImg = document.querySelector('#firstImg');

				firstImg.setAttribute('id', 'reviewImg');
				firstImg.setAttribute('src', getPathRootJump() + `/img/reviewimg/${review.img}`);
				
				reviewImgDiv.appendChild(makeX());		
				
			}


			// 평점 불러오기
			let starBtns = document.querySelectorAll('.starBtn');
			for (let i = 0; i < Number(review.star); i++) {
				let starImg = starBtns[i].childNodes[1];
				starImg.setAttribute('src', getPathRootJump() + '/img/star.svg.png');
			}
			document.querySelector('#starVal').value = review.star;

			// 리뷰 내용, 리뷰 넘버 불러오기
			let content = review.content;

			document.querySelector('#reviewUpdate textarea').value = content;
			document.querySelector('#reviewNo').value = review.no;

		})
		.catch(error => console.log(error));

}

// 팝업 닫기 콜백 
function closePopCallBack(e) {
	
		e.target.parentNode.parentNode.style.display = 'none';
	
		let starBtns = document.querySelectorAll('.starBtn');
		
		for (let i = 0; i < 5; i++) {
			let starImg = starBtns[i].childNodes[1];
			starImg.setAttribute('src', getPathRootJump() + '/img/eptstar.svg.png');
		}
	
		document.querySelector('#reviewUpdate textarea').value = '';
	

		let reviewImg = document.querySelector('#reviewImg');
		let imgFile = document.querySelector('#imgFile');
		let imgDelBtn = document.querySelector('#imgDelBtn');
			if (reviewImg) {
				reviewImg.setAttribute('id', 'firstImg');
				reviewImg.setAttribute('src', getPathRootJump() + `/img/emptyimg.jpg`);
				imgDelBtn.remove();
				imgFile.value = '';
			}
		
		let topBtn = document.querySelector('#topBtn');
		topBtn.style.display = 'block';
	
		}


// 절대 경로
function getPathRootJump() {
	var pathName = window.location.pathname.substring(1);
	var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
	var path_root = window.location.protocol + '//' + window.location.host + '/' + webName + '/';
	return path_root;
}