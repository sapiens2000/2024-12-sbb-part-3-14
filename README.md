# 2024-12-sbb-part-3-14

# 진행도 #

- [x] 답변 페이징과 정렬 기능 추가하기 
- [ ] 댓글 기능 추가하기
- [x] 카테고리 추가하기
- [ ] 비밀번호 찾기와 변경 기능 추가하기
- [X] 프로필 화면 구현하기
- [ ] 최근 답변과 최근 댓글 순으로 노출시키기
- [x] 조회 수 표시하기
- [ ] 소셜 미디어 로그인 기능 구현하기
- [ ] 마크다운 에디터 적용하기

# 생각해볼 점 #
## 카테고리 ##

---
* 카테고리는 관리자가 추가하게 만들기
* 게시판 구조 생각해보기 ex)카테고리 별로 나누기

## 조회수 ##

---
현재 Detail get 요청 시 조회수 단순 +1 처리중

* 조회수 중복 처리 할것인지
* 업데이트 바로 하기 or 배치처리 하기

## 답변 페이징 ##

---
* 추천순 정렬 오류수정 필요

## 프로필 ##

---
단순 질문 리스트 + 답변 리스트 보여주는 중