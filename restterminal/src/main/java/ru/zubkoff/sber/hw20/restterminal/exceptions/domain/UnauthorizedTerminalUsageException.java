package ru.zubkoff.sber.hw20.restterminal.exceptions.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedTerminalUsageException extends RuntimeException {
  public UnauthorizedTerminalUsageException() {
    super("call .login() firstly");
  }
}
