package com.santabooks.util;

public class Paging {
	private int curPage;

	private int totalCount;
	private int listCount;
	private int totalPage;

	private int pageCount;
	private int startPage;
	private int endPage;

	private int startNo;
	private int endNo;

	// 검색어
	private String search;
	// 제목? 내용? 제목 + 내용?
	private int searchNo;
	// 소설 카테고리
	private int category;
	// 소설 번호
	private int novelNo;
	// 회차 번호
	private int episodeNo;

	// count(*) 할 테이블명
	private String tableName;
	private int bookNo;

	private String searchType;
	private String keyword;

	// member 정보
	private int memberNo;
	private String memberId;
	private String memberNick;

	// 댓글 정렬
	private String orderType;

	public Paging() {

	}

	// 총 게시글 수를 입력받는 생성자
	public Paging(int totalCount) {
		this.setTotalCount(totalCount);

		this.makePaging();
	}

	// 총 게시글 수와 현재 페이지를 입력하는 생성자
	public Paging(int totalCount, int curPage) {
		this.setTotalCount(totalCount);
		this.setCurPage(curPage);

		this.makePaging();
	}

	// 총 게시글 수와 현재 페이지, 보여질 게시글 수를 입력하는 생성자
	public Paging(int totalCount, int curPage, int listCount) {
		this.setTotalCount(totalCount);
		this.setCurPage(curPage);
		this.setListCount(listCount);

		this.makePaging();
	}

	// 총 게시글 수와 현재 페이지, 보여질 게시글 수, 보여질 페이지 수를 입력하는 생성자
	public Paging(int totalCount, int curPage, int listCount, int pageCount) {
		this.setTotalCount(totalCount);
		this.setCurPage(curPage);
		this.setListCount(listCount);
		this.setPageCount(pageCount);

		this.makePaging();
	}

	// 페이징 정보 생성
	private void makePaging() {
		if (totalCount == 0)
			return; // 게시글이 없는 경우

		if (curPage < 1)
			setCurPage(1); // 첫 페이지 기본값 세팅
		if (pageCount < 1)
			setPageCount(10); // 첫 페이지 기본값 세팅
		if (listCount < 1)
			setListCount(10); // 첫 페이지 기본값 세팅

		// 총 페이지 수 계산
		totalPage = totalCount / listCount;
		if (totalCount % listCount > 0)
			totalPage++;
		// 현재 페이지 보정
		// 홈 페이지 번호보다 현재 페이지ㅂ 번호가 높을 때 총 페이지 번호로 고정
		if (totalPage < curPage)
			curPage = totalPage;

		// 화면에 보여질 페이징 시작번호와 끝번호
		startPage = ((curPage - 1) / pageCount) * pageCount + 1;
		endPage = startPage + pageCount - 1;

		// 계산된 끝 페이지 번호가 총 페이지 수보다 클 때 보정
		if (endPage > totalPage)
			endPage = totalPage;

		// 화면에 보여질 게시글의 시작번호와 끝번호
		startNo = (curPage - 1) * listCount + 1;
		endNo = curPage * listCount;
	}

	@Override
	public String toString() {
		return "Paging [curPage=" + curPage + ", totalCount=" + totalCount + ", listCount=" + listCount + ", totalPage="
				+ totalPage + ", pageCount=" + pageCount + ", startPage=" + startPage + ", endPage=" + endPage
				+ ", startNo=" + startNo + ", endNo=" + endNo + ", search=" + search + ", searchNo=" + searchNo
				+ ", category=" + category + ", novelNo=" + novelNo + ", episodeNo=" + episodeNo + ", tableName="
				+ tableName + ", bookNo=" + bookNo + ", searchType=" + searchType + ", keyword=" + keyword
				+ ", memberNo=" + memberNo + ", memberId=" + memberId + ", memberNick=" + memberNick + ", orderType="
				+ orderType + "]";
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getListCount() {
		return listCount;
	}

	public void setListCount(int listCount) {
		this.listCount = listCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getStartNo() {
		return startNo;
	}

	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}

	public int getEndNo() {
		return endNo;
	}

	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public int getSearchNo() {
		return searchNo;
	}

	public void setSearchNo(int searchNo) {
		this.searchNo = searchNo;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getNovelNo() {
		return novelNo;
	}

	public void setNovelNo(int novelNo) {
		this.novelNo = novelNo;
	}

	public int getEpisodeNo() {
		return episodeNo;
	}

	public void setEpisodeNo(int episodeNo) {
		this.episodeNo = episodeNo;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getBookNo() {
		return bookNo;
	}

	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberNick() {
		return memberNick;
	}

	public void setMemberNick(String memberNick) {
		this.memberNick = memberNick;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}