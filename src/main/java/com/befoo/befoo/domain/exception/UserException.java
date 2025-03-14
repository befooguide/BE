package com.befoo.befoo.domain.exception;

import com.befoo.befoo.domain.entity.User;

public class UserException extends RuntimeException {

  public UserException(String userId, String message) {
    super("Id: " + userId + " - " + message);
  }

  public UserException(User user, String message) {
    super("Id: " + user.getId() + " - " + message);
  }

    public static UserException notFound(String userId) {
        return new UserException(userId, "User를 찾을 수 없습니다.");
    }
}
