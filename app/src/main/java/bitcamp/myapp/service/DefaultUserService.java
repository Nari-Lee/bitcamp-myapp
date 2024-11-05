package bitcamp.myapp.service;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : bitcamp.myapp.service
 * fileName       : DefaultUserService
 * author         : narilee
 * date           : 24. 9. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 9. 11.        narilee       최초 생성
 * 24. 9. 24.        narilee       @Component 적용
 * 24. 9. 26.        narilee       @Transactional 적용
 * 24. 9. 30.        narilee       @Transactional 적용
 * 24. 10. 21.       narilee       lombok 적용
 */
@Component
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

  private final UserDao userDao;

  @Transactional
  public void add(User user) throws Exception {
      userDao.insert(user);
  }

  public List<User> list() throws Exception {
    return userDao.list();
  }

  public User get(int userNo) throws Exception {
    return userDao.findBy(userNo);
  }

  @Transactional
  public boolean update(User user) throws Exception {
    return userDao.update(user);
  }

  public boolean delete(int userNo) throws Exception {
    return userDao.delete(userNo);
  }

  public User exists(String email, String password) throws Exception {
    return userDao.findByEmailAndPassword(email, password);
  }
}
