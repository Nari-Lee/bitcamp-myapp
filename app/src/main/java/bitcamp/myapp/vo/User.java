package bitcamp.myapp.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * packageName    : bitcamp.myapp.vo
 * fileName       : User
 * author         : narilee
 * date           : 24. 8. 27.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 * 24. 9. 30.        narilee       Photo 추가
 * 24. 10. 21.       narilee       lombok 적용
 * 24. 10. 23.       narilee       EqualsAndHashCode 적용
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"no"})
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  private int no;
  private String name;
  private String email;
  private String password;
  private String tel;
  private String photo;

  public User(int no) {
    this.no = no;
  }
}
