package ru.zubkoff.sber.hw20.restterminal.domain.server;

public class AccessToken {

  private final long accountId;

  public long getAccountId() {
    return accountId;
  }

  public AccessToken(long accountId) {
    this.accountId = accountId;
  }
}
