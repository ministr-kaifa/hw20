package ru.zubkoff.sber.hw20.restterminal.controller;

import ru.zubkoff.sber.hw20.restterminal.domain.terminal.Terminal;

public record TerminalDto(Long id, Long clientId, Boolean isLogged) {
  public static TerminalDto fromEntity(Terminal terminal) {
    return new TerminalDto(terminal.getId(), terminal.getClientId(), terminal.isLogged());
  }
}
