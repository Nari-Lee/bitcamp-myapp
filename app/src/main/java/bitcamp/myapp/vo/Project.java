package bitcamp.myapp.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

/**
 * packageName    : bitcamp.myapp.vo
 * fileName       : Project
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : 프로젝트의 기본 정보를 포함하는 클래스입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 * 24. 10. 21.       narilee       lombok 적용
 */
@Data
public class Project implements Serializable {

  /**
   *데이터베이스와의 연동을 위해 Serializable을 구현
   */
  private static final long serialVersionUID = 1L;

  /** 프로젝트 번호 */
  private int no;
  /** 프로젝트 제목 */
  private String title;
  /** 프로젝트 설명 */
  private String description;
  /** 프로젝트 시작일 */
  private Date startDate;
  /** 프로젝트 종료일 */
  private Date endDate;
  /** 프로젝트 팀원*/
  private List<User> members;

}
