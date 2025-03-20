package com.befoo.befoo.domain.exception;

public class UserException extends RuntimeException {

  public UserException(String userId, String message) {
    super("Id: " + userId + " - " + message);
  }

    public static UserException notFound(String userId) {
        return new UserException(userId, "User를 찾을 수 없습니다.");
    }

    public static UserException alreadyExists(String username) {
        return new UserException(null, "username: " + username + " - 이미 존재하는 사용자 이름입니다.");
    }
}
