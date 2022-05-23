-- 카페
INSERT INTO cafe
values(1, 'Nice That', '대구시 ~ ', '053-123-1234', 'nicethat.jpeg', '수성구');
INSERT INTO cafe
values(2, 'In The Mass', '대구시 ~ ', '053-234-2345', 'inthemass.png', '북구');
INSERT INTO cafe
values(3, 'EERT', '대구시 ~ ', '053-345-3456', 'eert.jpeg', '수성구');
INSERT INTO cafe
values(4, 'roller', '대구시 ~ ', '053-456-4567', 'roller.jpeg', '서구');
INSERT INTO cafe
values(5, '검은 구상', '대구시 ~ ', '053-567-5678', 'black.jpeg', '북구');

-- 광고 카페
INSERT INTO ad_cafe
VALUES (1, '지구와 비슷한 환경의 행성으로 이주한다면"이라는 가정에서 시작되어 재미있는 상상으로 기획된 나이스댓. 지구에서 가져온 돌, 나무, 식물, 흙을 수집하는 셀렉터가 미래에서 과거를 재현했다는 설정의 재미있는 공간입니다. 많이 공감해주시고 즐겨주세요!');
INSERT INTO ad_cafe
VALUES (2, '우리는 좋은 커피를 만드는 사람들입니다. 인더매스 로스터와 바리스타는 좋은 커피 한 잔을 위해 각 산지에서 수확한 생두가 커피 한 잔이 되기까지의 모든 과정과 결과에 집중합니다. 모든 곳에서 일관적인 맛과 향의 커피 한 잔을 위하여 생두 선별, 산지와의 직접 계약을 통한 균일한 품질 관리와 끊임없는 연구개발을 바탕으로 최상의 로스팅은 물론, 최상의 커피 추출을 위해 노력합니다.');
INSERT INTO ad_cafe
VALUES (3, '이이알티는 도심 속의 쉼이라는 주제로 서울숲에서 처음 만들어진 카페입니다. 서울숲을 시작으로 마곡, 망원, 대구에 차례대로 분점이 생겼는데 모든 매장이 쉼이라는 공통 주제를 가지고 장소가 가지고 있는 장점을 살린 인테리어로 사랑받고 있습니다.');
INSERT INTO ad_cafe
VALUES (4, '대구의 인쇄소가 밀집된 골목. 오래된 건물 사이로 지게차가 오가는 이곳에 작은 카페가 있다. 롤러 커피의 롤러는 인쇄할 때 종이를 누르는 도구에서 착안했다. 사장의 커피 고집이 깐깐해서 직접 커피를 로스팅 한다. 롤러 모양의 번호표, ‘비락우유’라고 적힌 빈티지한 유리컵을 사용하는 것도 재미있다.');

-- 리뷰
INSERT INTO review
values(1, review_seq.NEXTVAL, 'seok@gmail.com', '석경진', 'prof1.jpeg', '2021-12-14', 5, 3, '한번은 꼭 가보고 싶었지만, 그냥 쏘쏘..', 'review10.jpeg');
INSERT INTO review
values(1, review_seq.NEXTVAL, 'imdain@naver.com', '다인6422', 'prof2.jpeg', '2022-01-07', 8, 3, '유행에 뒤쳐지지않으려 맘먹고 와봤다.
음..대구끝까지 온 보람은 음씀ㅎㅎ 가격에비해양이너무작아요...
우리입맛에는 가성비맛둘다잡은 ㅅㅇㅈ커피가 더맛있다ㅎㅎㅅㅎ', 'review9.jpeg');
INSERT INTO review
values(2, review_seq.NEXTVAL, 'mango@gmail.com', '망고러브', 'basic.png', '2022-02-25', 4, 1, '4명이 방문해서 음료4잔 케익 두개 주문함 임산부가 있어서 디카페인 주문함 
아이스주문한거같은데 따뜻한거로 나옴 그래서 확인하고 내가 잘못 주문했을수도 있고 직원이 잘못 한거 일수도 있음 일단 그거를 떠나서 그럼 얼음잔을 달라고 하니 
안 줌 그래서 그럼 아이스잔에다가 뜨거운거 좀 부어 달라고 함 그래도 안해줌 뜨거운거에 얼음 몇개 넣어주니 바로 녹아서 그럼 커피를 좀 버리고 얼음 넣어 달라고 함 
아니 새로 음료 만들어 달라는거도 아니고 무조건 컵은 따로 제공할수 없다고 함 그럼 아이스컵 만들어서 주문한거 좀 부어 달래도 안해줌 그래서 내가 참나 이랬더니 자기보고 
욕했냐고 싸울려고 듬 4명이서 2잔 시키고 컵을 더 달라고 한것도 아니고 설령 내가 주문을 잘못했다해도 아니 컵에 얼음 좀 주는게 그렇게 어려운일인가', null);
INSERT INTO review
values(2, review_seq.NEXTVAL, '606@naver.com', '육공육', 'prof4.jpeg', '2022-03-22', 20, 4, '- 접근성: 좋지 않다. 주차 공간은 넓어 운전자분들이 가기 좋아요.
- 맛: (과테말라, 케냐) 보통.
- 친절도: 보통
- 인테리어 및 브랜딩: 일관성있는 인테리어와 제품, 컵 등으로 보기 좋음. 테이블 간격 넓음. 4인 이상 테이블이 많아 좋았음.
- 일회용 컵 써서 매우 아쉽. 사람들도 많이 오던데 버려질 일회용품들 생각하니 좀 그래요..', 'review8.jpeg');
INSERT INTO review
values(2, review_seq.NEXTVAL, 'bemve@naver.com', '벰베로', 'prof5.jpeg', '2022-03-28', 12, 3, '항상맛잇어요!아메리카노가없는게 신기해요!
근데 매장에 들어서면 커피냄새가아닌 화장실냄새가나요 ㅠ로팅룸문열어두심 커피냄새 향긋할텐데 ㅠ 첨엔 잘못맡았나싶었는데 이번방문에도 ㅠ', 'review7.jpeg');
INSERT INTO review
values(2, review_seq.NEXTVAL, 'imssg1@naver.com', 'imssg1', 'prof3.png', '2022-04-02', 16, 4, '주말이라 사람이 많더라구요
안에 좌석은 한두자리정도? 비어있었던것같은데 금방금방찼어요
테라스자리는 있어서 앉았는데 그늘이고 바람 좀불고 아이스크림까지 먹으니 춥더라구요 ㅎ
하나는 따뜻한 커피가 더 좋을듯 싶었네요^-^
아이스크림 맛있었구요 화장실도 깨끗했고 직원분도 친절하셨어요
바로 옆에 쭈꾸미집이 있어서 저녁도 편하게 해결했네요:)', 'review1.jpeg');
INSERT INTO review
values(3, review_seq.NEXTVAL, 'baram@daum.net', '바람을꿈꾸다', 'prof6.jpeg', '2022-04-05', 9, 4, '누군가의 사진으로만 보던 곳을 드디어 와보네요.
직접 로스팅하는 곳은 드립커피 가격이 좀 저렴한 편이던데, 여긴 그렇진 않네요. 대신 음료 한 잔당 드립백이 하나씩 서비스로 제공되고 있어요.
에티오피아와 케냐AA 주문 했는데~ 에티오피아는 무난하네요~ ㅎ 아이스크림이 맛있다하니, 한 번 더 들러봐야겠어요~
굉장히 쾌적하고 평일이라 그런지 한산해서 더 좋았어요~ ^^', 'review2.jpeg');
INSERT INTO review
values(3, review_seq.NEXTVAL, 'binggla@gmail.com', '빙글랑', 'basic.png', '2022-04-15', 10, 5, '멀어도 가끔씩 오는 이유는 매장도 넓고 산미있는 아.아, 아이스크림커피 음료가 다 맛있기 때문.
샘플팩도 1주문1팩 주시니 기분도 좋아지는 서비스예요~ 날씨가 좋으니 야외테라스 이용하시에도 좋습니다.', 'review4.jpeg');
INSERT INTO review
values(4, review_seq.NEXTVAL, 'abcd@naver.com', '예굴개굴', 'prof8.jpeg', '2022-05-01', 8, 5, '아이스크림 라떼 정말 맛있어요 :-) 
주말에 방문해서 자리 잡느라 고생했지만 핫한건 다이유가 있네요. 기찻길 보며 운치있게 잘 마셨어요.', 'review3.jpeg');
INSERT INTO review
values(4, review_seq.NEXTVAL, '353562@naver.com', '트루리36', 'prof9.jpeg', '2022-05-12', 4, 5, '커피가 맛있어요. 드립샘플 쥬시는것도 좋규요', 'review5.jpeg');
INSERT INTO review
values(4, review_seq.NEXTVAL, 'jinnie@naver.com', '현지니지니', 'basic.png', '2022-05-18', 2, 5, '벌써 3번째 구매 한달에 한번 씩 꼭 지나가면 들리는 곳 ㅎㅎㅎ 다른커피 이제 못먹어서 어쩌나몰라유', 'review6.jpeg');
values(4, review_seq.NEXTVAL, 'mango@gmail.com', '망고러브', 'basic.png', '2022-02-25', 4, 1, '4명이 방문해서 음료4잔 케익 두개 주문함 임산부가 있어서 디카페인 주문함 
아이스주문한거같은데 따뜻한거로 나옴 그래서 확인하고 내가 잘못 주문했을수도 있고 직원이 잘못 한거 일수도 있음 일단 그거를 떠나서 그럼 얼음잔을 달라고 하니 
안 줌 그래서 그럼 아이스잔에다가 뜨거운거 좀 부어 달라고 함 그래도 안해줌 뜨거운거에 얼음 몇개 넣어주니 바로 녹아서 그럼 커피를 좀 버리고 얼음 넣어 달라고 함 
아니 새로 음료 만들어 달라는거도 아니고 무조건 컵은 따로 제공할수 없다고 함 그럼 아이스컵 만들어서 주문한거 좀 부어 달래도 안해줌 그래서 내가 참나 이랬더니 자기보고 
욕했냐고 싸울려고 듬 4명이서 2잔 시키고 컵을 더 달라고 한것도 아니고 설령 내가 주문을 잘못했다해도 아니 컵에 얼음 좀 주는게 그렇게 어려운일인가', null);
INSERT INTO review
values(4, review_seq.NEXTVAL, '606@naver.com', '육공육', 'prof4.jpeg', '2022-03-22', 20, 4, '- 접근성: 좋지 않다. 주차 공간은 넓어 운전자분들이 가기 좋아요.
- 맛: (과테말라, 케냐) 보통.
- 친절도: 보통
- 인테리어 및 브랜딩: 일관성있는 인테리어와 제품, 컵 등으로 보기 좋음. 테이블 간격 넓음. 4인 이상 테이블이 많아 좋았음.
- 일회용 컵 써서 매우 아쉽. 사람들도 많이 오던데 버려질 일회용품들 생각하니 좀 그래요..', 'review8.jpeg');
INSERT INTO review
values(4, review_seq.NEXTVAL, 'bemve@naver.com', '벰베로', 'prof5.jpeg', '2022-03-28', 12, 3, '항상맛잇어요!아메리카노가없는게 신기해요!
근데 매장에 들어서면 커피냄새가아닌 화장실냄새가나요 ㅠ로팅룸문열어두심 커피냄새 향긋할텐데 ㅠ 첨엔 잘못맡았나싶었는데 이번방문에도 ㅠ', 'review7.jpeg');
INSERT INTO review
values(5, review_seq.NEXTVAL, 'imssg1@naver.com', 'imssg1', 'prof3.png', '2022-04-02', 16, 4, '주말이라 사람이 많더라구요
안에 좌석은 한두자리정도? 비어있었던것같은데 금방금방찼어요
테라스자리는 있어서 앉았는데 그늘이고 바람 좀불고 아이스크림까지 먹으니 춥더라구요 ㅎ
하나는 따뜻한 커피가 더 좋을듯 싶었네요^-^
아이스크림 맛있었구요 화장실도 깨끗했고 직원분도 친절하셨어요
바로 옆에 쭈꾸미집이 있어서 저녁도 편하게 해결했네요:)', 'review1.jpeg');