# Dividend
## 배당금 프로젝트 
#### 내가 궁금한 회사를 검색하여 배당금 지급 내역을 확인해 보세요!
#### 프로젝트 기간 : 7.28 ~ 
#### 인원: 개인프로젝 
#### 사용한 웹사이트 : 야후파이넨스


# API 명세서
# 🎯 배당금 API

## ✅ GET/finance/dividend?{companyName}
### 특정 회사의 배당금 내역 조회
#### {companyName : "좋은회사", dividend :[{date:"2020.3.21",price:"2.00",..}]}
#### 회사 이름과 배당금 지급 내역을 보여줌

## ✅ GET/company/autocomplete?keyword=O
### 배당금 검색 - 자동완성
#### keyword 파라미터로 배당금 이름을 검색하면 {result:["O","OAS",...]}
와 같이 해당 글이 들어간 배당금 키워드를 반환한다.


## ✅GET/company 
### 회사 리스트 조회
#### {result : [{companyName: "좋은회사",ticker : "GOOD"},{companyName:"a",ticker:"b"},...]}


## ✅ POST/company
### 배당금 저장
#### {ticker : "GOOD"} ticker 파라미터로 받아주세요
#### DB에 {ticker : "GOOD", companyName : "좋은회사"} 이렇게 저장합니다.

## ✅ DELETE/company?ticker=GOOD
### 배당금 삭제

# 🎯 회원 API
## ✅ 회원가입
## ✅ 로그인
## ✅ 로그아웃
