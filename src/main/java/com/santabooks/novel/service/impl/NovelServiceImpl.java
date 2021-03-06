package com.santabooks.novel.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.santabooks.member.dto.Member;
import com.santabooks.novel.dao.face.NovelDao;
import com.santabooks.novel.dto.Comment;
import com.santabooks.novel.dto.Episode;
import com.santabooks.novel.dto.Favorite;
import com.santabooks.novel.dto.Novel;
import com.santabooks.novel.dto.Score;
import com.santabooks.novel.service.face.NovelService;
import com.santabooks.util.Paging;

@Service
public class NovelServiceImpl implements NovelService {

	private static final Logger logger = LoggerFactory.getLogger(NovelServiceImpl.class);

	@Autowired
	private NovelDao novelDao;
	@Autowired
	private ServletContext context;

	@Override
	public List<Novel> getMyNovel(Paging paging) {
		return novelDao.selectMyNovel(paging);
	}

	@Override
	public List<Novel> getMyNovelByFavorite(Paging paging) {
		return novelDao.selectMyNovelByFavorite(paging);
	}

	@Override
	public void addNovel(Novel novel) {
		novelDao.insertNovel(novel);

		fileSave(novel);
	}

	@Override
	public void modifyNovel(Novel novel) {
		novelDao.updateNovel(novel);

		if (novel.getUpload() != null && !novel.getUpload().isEmpty()) {
			Novel result = novelDao.selectNovelByNovelNo(novel.getNovelNo());

			fileDelete(result);
		}

		fileSave(novel);
	}

	@Override
	public void removeNovel(Novel novel) {
		// comment 삭제즐겨찾기 삭제별점 삭제에피소드 삭제게시글 삭제 사진 파일 삭제
		// 참조조건으로 삭제설정했음
		Novel result = novelDao.selectNovelByNovelNo(novel.getNovelNo());

		novelDao.deleteNovel(novel);

		fileDelete(result);
	}

	@Override
	public List<Novel> getNovelList(Paging paging) {
		return novelDao.selectNovelList(paging);
	}

	@Override
	public List<Novel> getBestNovel(int category) {
		return novelDao.selectBestNovel(category);
	}

	@Override
	public void addEpisode(Episode episode) {
		novelDao.insertEpisode(episode);
	}

	@Override
	public void modifyEpisode(Episode episode) {
		novelDao.updateEpisode(episode);
	}

	@Override
	public void removeEpisode(Episode episode) {
		novelDao.deleteEpisode(episode);
	}

	@Override
	public List<Episode> getEpisodeList(Paging paging) {
		return novelDao.selectEpisodeByNovelNo(paging);
	}

	@Override
	public Paging getPaging(Paging paging) {
		Paging resultPaging = new Paging(novelDao.selectCntList(paging), paging.getCurPage(), 12);
		resultPaging.setSearch(paging.getSearch());
		resultPaging.setSearchNo(paging.getSearchNo());
		resultPaging.setTableName(paging.getTableName());
		resultPaging.setCategory(paging.getCategory());
		resultPaging.setNovelNo(paging.getNovelNo());
		resultPaging.setMemberNo(paging.getMemberNo());
		resultPaging.setEpisodeNo(paging.getEpisodeNo());

		return resultPaging;
	}

	@Override
	public Novel getNovelByNovelNo(int novelNo) {
		return novelDao.selectNovelByNovelNo(novelNo);
	}

	@Override
	public Episode getEpisode(Episode episode) {
		return novelDao.selectEpisodeByEpisodeNo(episode);
	}

	@Override
	public void fileSave(Novel novel) {
		MultipartFile file = novel.getUpload();

		// 파일이 저장될 경로
		String storedPath = context.getRealPath("upload");

		// UUID
		String uid = UUID.randomUUID().toString().split("-")[4];

		// 저장될 파일의 이름 (원본명 + UUID)
		String fileName = uid + "_" + file.getOriginalFilename();

		boolean check = false;

		// 확장자 구분
		if (fileName.substring(fileName.indexOf(".") + 1).equals("jpeg")) {
			check = true;
		} else if (fileName.substring(fileName.indexOf(".") + 1).equals("png")) {
			check = true;
		} else if (fileName.substring(fileName.indexOf(".") + 1).equals("mpg")) {
			check = true;
		} else if (fileName.substring(fileName.indexOf(".") + 1).equals("jpg")) {
			check = true;
		} else {
			logger.info("확장자 없음");
		}

		// 저장될 파일 객체
		File dest = new File(storedPath, fileName);

		try {
			if (check && file != null && !file.isEmpty()) {
				// 실제 파일 저장
				file.transferTo(dest);

				novel.setImgOriginName(file.getOriginalFilename());
				novel.setImgStoredName(fileName);

				novelDao.updateNovelImg(novel);
			} else {
				logger.info("파일 저장 실패 : 확장자 혹은 파일이 존재하지 않습니다.");
			}

		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fileDelete(Novel novel) {
		File file = new File(context.getRealPath("upload"), "" + novel.getImgStoredName());

		if (file.exists()) {
			file.delete();
		}
	}

	@Override
	public Score addScore(Score score) {
		int cntResult = novelDao.selectCntScoreByMemberNo(score);

		if (cntResult != 1) {
			// 남긴 별점이 없을 경우
			novelDao.insertScore(score);
		} else {
			// 이미 남긴 별점이 있을 경우
			novelDao.updateScore(score);
		}

		novelDao.updateEpisodeScore(score);

		return novelDao.selectScore(score);
	}

	@Override
	public Score removeScore(Score score) {
		novelDao.deleteScore(score);

		novelDao.updateEpisodeScore(score);

		return novelDao.selectScore(score);
	}

	@Override
	public Score getMyScore(Score score) {
		return novelDao.selectScoreByMemberNo(score);
	}

	@Override
	public boolean isFavorite(Favorite favorite) {
		int cnt = novelDao.selectCntFavoriteByMemberNo(favorite);

		if (cnt > 0) {
			// 즐겨찾기한 상태
			return true;
		} else {
			// 즐겨찾기 안한 상태
			return false;
		}
	}

	@Override
	public boolean favorite(Favorite favorite) {
		if (isFavorite(favorite)) {
			// 이미 즐겨찾기를 한 상태
			novelDao.deleteFavorite(favorite);
			return false;
		} else { // 즐겨찾기를 하지 않은 상태
			novelDao.insertFavorite(favorite);
			return true;
		}
	}

	@Override
	public int getTotalCntFavorite(Favorite favorite) {
		return novelDao.selectCntFavorite(favorite);
	}

	@Override
	public List<Comment> getCommentList(Paging paging) {
		return novelDao.selectComment(paging);
	}
	
	@Override
	public List<Comment> getReplyList(Comment comment) {
		return novelDao.selectReply(comment);
	}

	@Override
	public void addComment(Comment comment) {
		novelDao.insertComment(comment);
	}

	@Override
	public void removeComment(Comment comment) {
		Comment result = novelDao.selectCommentByCommentNo(comment);
		
		if (result.getParentCmtNo() != 0) {
			novelDao.updateCommentSeqDelete(comment);
		}
		
		novelDao.deleteComment(comment);
	}
	
	@Override
	public void addReply(Comment comment) {
		novelDao.updateCommentSeqInsert(comment);
		
		comment.setSeq(2);
		
		novelDao.insertComment(comment);
	}
	
	@Override
	public void modifyComment(Comment comment) {
		novelDao.updateComment(comment);
	}
	
	@Override
	public List<Comment> getMyComment(Comment comment) {
		return novelDao.selectMyComment(comment);
	}
}
