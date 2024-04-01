package ru.zubkoff.sber.hw20.restterminal.exceptions.domain;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class AccountIsLockedException extends RuntimeException {

  private final Instant lockedUntil;
  private final Instant throwedAt;

  public AccountIsLockedException(Instant lockedUntil, Instant throwedAt) {
    super("Lock will expire in " + (lockedUntil.getEpochSecond() - throwedAt.getEpochSecond()) + " seconds");
    this.lockedUntil = lockedUntil;
    this.throwedAt = throwedAt;
  }

  public Instant getThrowedAt() {
    return throwedAt;
  }

  public Instant getLockedUntil() {
    return lockedUntil;
  }

}
