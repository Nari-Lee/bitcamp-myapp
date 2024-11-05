package bitcamp.myapp.service;

import bitcamp.myapp.vo.Project;

import java.util.List;

/**
 * packageName    : bitcamp.myapp.service
 * fileName       : ProjectService
 * author         : narilee
 * date           : 24. 9. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 9. 12.        narilee       최초 생성
 */
public interface ProjectService {

  void add(Project project) throws Exception;

  List<Project> list() throws Exception;

  Project get(int projectNo) throws Exception;

  boolean update(Project project) throws Exception;

  boolean delete(int projectNo) throws Exception;
}
