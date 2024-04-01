package ru.zubkoff.sber.hw20.restterminal.exceptions.domain;

public class InvalidAccessTokenException extends RuntimeException {
  public InvalidAccessTokenException() {
    super("Something vent wrong, try relogin");
  }
}
