package bitcamp.myapp.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * packageName    : bitcamp.myapp.service
 * fileName       : StorageService
 * author         : narilee
 * date           : 24. 9. 30.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 9. 30.        narilee       최초 생성
 */
public interface StorageService {
  String CONTENT_TYPE = "ContentType";

  void upload(String filePath, InputStream in, Map<String, Object> options) throws Exception;
  void delete(String filePath) throws Exception;
  void download(String filePath, OutputStream out) throws Exception;
}
