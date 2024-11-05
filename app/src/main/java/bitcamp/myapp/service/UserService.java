package bitcamp.myapp.service;

import bitcamp.myapp.vo.User;

import java.util.List;

/**
 * packageName    : bitcamp.myapp.service
 * fileName       : UserService
 * author         : narilee
 * date           : 24. 9. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 9. 11.        narilee       최초 생성
 */
public interface UserService {

  void add(User user) throws Exception;

  List<User> list() throws Exception;

  User get(int userNo) throws Exception;

  boolean update(User user) throws Exception;

  boolean delete(int userNo) throws Exception;

  User exists(String email, String password) throws Exception;
}
