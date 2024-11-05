package bitcamp.myapp.vo;

import lombok.Data;

/**
 * packageName    : bitcamp.myapp.vo
 * fileName       : AttachedFile
 * author         : narilee
 * date           : 24. 9. 9.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 9. 9.        narilee       최초 생성
 * 24. 10. 21.      narilee       lombok 적용
 */
@Data
public class AttachedFile {
  private int fileNo;
  private String filename;
  private String originFilename;
  private int boardNo;
}
