package ru.zubkoff.sber.hw20.restterminal.domain.terminal;

import ru.zubkoff.sber.hw20.restterminal.domain.server.AccessToken;
import ru.zubkoff.sber.hw20.restterminal.domain.server.TerminalServer;
import ru.zubkoff.sber.hw20.restterminal.exceptions.domain.InvalidPinException;
import ru.zubkoff.sber.hw20.restterminal.exceptions.domain.UnauthorizedTerminalUsageException;

public class Terminal {

  private final long id;
  private final long clientId;
  private final PinValidator pinValidator;
  private final TerminalServer server;
  private AccessToken accessToken;

  public Terminal(long id, long accountId, PinValidator pinValidator, TerminalServer server) {
    this.id = id;
    this.clientId = accountId;
    this.pinValidator = pinValidator;
    this.server = server;
  }

  public boolean isLogged() {
    return accessToken != null;
  }

  public void login(String pin) {
    if (pinValidator.isValidPin(pin)) {
      accessToken = server.login(clientId, pin);
    } else {
      throw new InvalidPinException(pin);
    }
  }

  public void updateBalance(double diff) {
    if (accessToken == null) {
      throw new UnauthorizedTerminalUsageException();
    }
    server.updateBalance(accessToken, diff);
  }

  public double getBalance() {
    if (accessToken == null) {
      throw new UnauthorizedTerminalUsageException();
    }
    return server.getBalance(accessToken);
  }

  public void logout() {
    accessToken = null;
  }

  public Long getClientId() {
    return clientId;
  }

  public PinValidator getPinValidator() {
    return pinValidator;
  }

  public TerminalServer getServer() {
    return server;
  }

  public AccessToken getAccessToken() {
    return accessToken;
  }

  public long getId() {
    return id;
  }

  public void setAccessToken(AccessToken accessToken) {
    this.accessToken = accessToken;
  }

}
