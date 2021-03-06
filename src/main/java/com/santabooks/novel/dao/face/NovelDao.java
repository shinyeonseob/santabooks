package com.santabooks.novel.dao.face;

import java.util.List;

import com.santabooks.member.dto.Member;
import com.santabooks.novel.dto.Comment;
import com.santabooks.novel.dto.Episode;
import com.santabooks.novel.dto.Favorite;
import com.santabooks.novel.dto.Novel;
import com.santabooks.novel.dto.Score;
import com.santabooks.util.Paging;

public interface NovelDao {

	/**
	 * memberNo로 작성한 소설 조회
	 * 
	 * @param novel - memberNo 필요
	 * @return List<Novel> - 결과값
	 */
	public List<Novel> selectMyNovel(Paging paging);

	/**
	 * memberNo로 즐겨찾기한 소설 조회
	 * 
	 * @param member - memberNo 필요
	 * @return List<Novel> - 결과값
	 */
	public List<Novel> selectMyNovelByFavorite(Paging paging);

	/**
	 * 게시글 총 개수를 구하는 메소드
	 * 
	 * @param paging - 검색어가 있다면 담겨있을 DTO
	 * @return int - 결과 cnt
	 */
	public int selectCntList(Paging paging);

	/**
	 * 소설 목록을 가져오는 메소드
	 * 
	 * @param paging - Paging 객체
	 * @return List<Novel> - 결과 LIST
	 */
	public List<Novel> selectNovelList(Paging paging);

	/**
	 * 별점순으로 3개 항목 가져오기
	 * 
	 * @param category - 카테고리
	 * 
	 * @return List<Novel> - 결과 목록
	 */
	public List<Novel> selectBestNovel(int category);

	/**
	 * 소설 정보 가져오기
	 * 
	 * @param paging - 소설번호 DTO
	 * @return Novel - 소설 정보
	 */
	public Novel selectNovelByNovelNo(int novelNo);

	/**
	 * 소설 회차보는 쿼리
	 * 
	 * @param paging - 소설 번호가 담겨있는 DTO
	 * @return List<Episode> - 회차 목록
	 */
	public List<Episode> selectEpisodeByNovelNo(Paging paging);

	/**
	 * 회차 상세보기
	 * 
	 * @param episode - episodeNo
	 * @return Episode - 회차 정보
	 */
	public Episode selectEpisodeByEpisodeNo(Episode episode);

	/**
	 * 소설 등록하기
	 * 
	 * @param novel - 소설 정보
	 */
	public void insertNovel(Novel novel);

	/**
	 * 소설 소개 수정하기
	 * 
	 * @param novel - 소설 수정 내용
	 */
	public void updateNovel(Novel novel);

	/**
	 * comment 삭제 즐겨찾기 삭제 별점 삭제 에피소드 삭제 소설 삭제
	 * 
	 * @param novel - novelNo로 전부 삭제하기
	 */
	public void deleteNovel(Novel novel);

	/**
	 * 소설 표지 설정하기
	 * 
	 * @param novel - 파일 정보
	 */
	public void updateNovelImg(Novel novel);

	/**
	 * 회차 등록하기
	 * 
	 * @param episode - 연재내용
	 */
	public void insertEpisode(Episode episode);

	/**
	 * 회차 수정하기
	 * 
	 * @param episode - 수정 내용
	 */
	public void updateEpisode(Episode episode);

	/**
	 * 회차 삭제하기
	 * 
	 * @param episode
	 */
	public void deleteEpisode(Episode episode);

	/**
	 * 이미 평가한 평점이 있는지 확인하는 쿼리
	 * 
	 * @param score - 별점 정보 DTO (episodeNo, memberNo)
	 * @return - cnt(score)
	 */
	public int selectCntScoreByMemberNo(Score score);

	/**
	 * 내가 별점을 몇점줬는지 확인하기
	 * 
	 * @param score - memberNo, episodeNo
	 * @return - 결과
	 */
	public Score selectScoreByMemberNo(Score score);

	/**
	 * 별점 수정하는 쿼리
	 * 
	 * @param score - 수정 내용 DTO
	 */
	public void updateScore(Score score);

	/**
	 * 별점 수정하기 (회차)
	 * 
	 * @param score - 수정 정보
	 */
	public void updateEpisodeScore(Score score);

	/**
	 * 별점 등록하기 (회차)
	 * 
	 * @param score - 별점 정보 DTO
	 */
	public void insertScore(Score score);

	/**
	 * 별점 삭제하는 기능
	 * 
	 * @param score - episodeNo, memberNo
	 */
	public void deleteScore(Score score);

	/**
	 * 회차의 점수 조회하는 쿼리
	 * 
	 * @param score - 회차 정보
	 * @return 점수
	 */
	public Score selectScore(Score score);

	/**
	 * 즐겨찾기 count하는 쿼리
	 * 
	 * @param favorite - novelNo
	 * @return 결과값
	 */
	public int selectCntFavorite(Favorite favorite);

	/**
	 * 내가 즐겨찾기했는지 count하는 쿼리
	 * 
	 * @param favorite - novelNo, memberNo
	 * @return 결과값
	 */
	public int selectCntFavoriteByMemberNo(Favorite favorite);

	/**
	 * 즐겨찾기 추가하기
	 * 
	 * @param favorite - memberNo, novelNo
	 */
	public void insertFavorite(Favorite favorite);

	/**
	 * 즐겨찾기 삭제하기
	 * 
	 * @param favorite - memberNo, novelNo
	 */
	public void deleteFavorite(Favorite favorite);

	/**
	 * 댓글 리스트 가져오기 (reply 제외)
	 * 
	 * @param comment - episodeNo
	 * @return 조회 결과
	 */
	public List<Comment> selectComment(Paging paging);

	/**
	 * 답글 리스트 가져오기 (reply)
	 * 
	 * @param comment - episodeNo, parentCmtNo
	 * @return
	 */
	public List<Comment> selectReply(Comment comment);

	/**
	 * 댓글 작성하기
	 * 
	 * @param comment - episodeNo, memberNo, content 기본
	 */
	public void insertComment(Comment comment);

	/**
	 * 댓글 삭제하기
	 * 
	 * @param comment - commentNo
	 */
	public void deleteComment(Comment comment);

//	/**
//	 * 답글 insert
//	 * 
//	 * @param comment - content, parentCmtNo, memberNo
//	 */
//	public void insertReply(Comment comment);

	public void updateCommentSeqInsert(Comment comment);

	public void updateCommentSeqDelete(Comment comment);

	/**
	 * 댓글 수정하는 쿼리
	 * 
	 * @param comment - content, commentNo
	 */
	public void updateComment(Comment comment);

	/**
	 * 댓글 하나 조회하기
	 * 
	 * @param comment - commentNo
	 * @return
	 */
	public Comment selectCommentByCommentNo(Comment comment);

	/**
	 * 내 댓글 가져오기
	 * 
	 * @param comment - memberNo
	 * @return
	 */
	public List<Comment> selectMyComment(Comment comment);

}
