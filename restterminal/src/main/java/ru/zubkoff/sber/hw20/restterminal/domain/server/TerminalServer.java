package ru.zubkoff.sber.hw20.restterminal.domain.server;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ru.zubkoff.sber.hw20.restterminal.exceptions.domain.AccountIsLockedException;
import ru.zubkoff.sber.hw20.restterminal.exceptions.domain.FailedBalanceManipulationException;
import ru.zubkoff.sber.hw20.restterminal.exceptions.domain.InvalidAccessTokenException;
import ru.zubkoff.sber.hw20.restterminal.exceptions.domain.WrongCredentialsException;

@Component
public class TerminalServer {

  private static final Duration LOCK_TIME = Duration.ofSeconds(10);
  private static final long MAX_LOGIN_TRIES = 3;

  private final Map<Long, Account> accounts;
  private final Map<Long, Integer> unsuccessfulTries;

  public TerminalServer(List<Account> accounts) {
    this.accounts = accounts.stream()
        .collect(Collectors.toMap(
            Account::getId,
            account -> account));
    unsuccessfulTries = new HashMap<>();
  }

  private Account getAccount(long accountId) {
    var account = accounts.get(accountId);
    if (account == null) {
      throw new WrongCredentialsException();
    }
    if (account.isLocked()) {
      throw new AccountIsLockedException(account.getLockedUntil().get(), Instant.now());
    }
    return account;
  }

  public AccessToken login(long accountId, String pin) {
    var account = getAccount(accountId);
    if (!account.getPin().equals(pin)) {
      if (unsuccessfulTries.containsKey(accountId) && unsuccessfulTries.get(accountId) == MAX_LOGIN_TRIES - 1) {
        account.lock(Instant.now().plusSeconds(LOCK_TIME.toSeconds()));
        unsuccessfulTries.remove(accountId);
        throw new AccountIsLockedException(account.getLockedUntil().get(), Instant.now());
      } else {
        unsuccessfulTries.put(accountId, 1 + unsuccessfulTries.getOrDefault(accountId, 0));
      }
      throw new WrongCredentialsException();
    }
    return new AccessToken(accountId);
  }

  public void updateBalance(AccessToken accessToken, double diff) {
    Account account = null;
    try {
      account = getAccount(accessToken.getAccountId());
    } catch (WrongCredentialsException e) {
      throw new InvalidAccessTokenException();
    }
    if (account.getBalance() + diff < 0 || diff % 100 != 0) {
      throw new FailedBalanceManipulationException();
    }
    account.setBalance(account.getBalance() + diff);
  }

  public double getBalance(AccessToken accessToken) {
    Account account = null;
    try {
      account = getAccount(accessToken.getAccountId());
    } catch (WrongCredentialsException e) {
      throw new InvalidAccessTokenException();
    }
    return account.getBalance();
  }

}
