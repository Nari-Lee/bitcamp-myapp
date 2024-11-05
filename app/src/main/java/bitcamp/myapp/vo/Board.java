package bitcamp.myapp.vo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * packageName    : bitcamp.myapp.vo
 * fileName       : Board
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : 게시판의 기본 정보를 포함하는 클래스입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 * 24. 9. 09.        narilee       파일 추가
 * 24. 10. 21.       narilee       lombok 적용
 */
@Data
public class Board implements Serializable {

  /**
   *데이터베이스와의 연동을 위해 Serializable을 구현
   */
  private static final long serialVersionUID = 1L;

  /** 게시물 번호 */
  private int no;
  /** 게시물 제목 */
  private String title;
  /** 게시물 내용 */
  private String content;
  /** 게시물 작성자 */
  private User writer;
  /** 게시물 작성일 */
  private Date createdDate;
  /** 게시물 조회수 */
  private int viewCount;
  /** 첨부 파일 */
  private List<AttachedFile> attachedFiles;

}
