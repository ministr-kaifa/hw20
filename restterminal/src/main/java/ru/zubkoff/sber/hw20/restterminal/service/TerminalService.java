package ru.zubkoff.sber.hw20.restterminal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ru.zubkoff.sber.hw20.restterminal.domain.terminal.Terminal;

@Service
public class TerminalService {

  private final Map<Long, Terminal> terminals;

  public TerminalService(List<Terminal> terminals) {
    this.terminals = terminals.stream()
        .collect(Collectors.toMap(Terminal::getId, terminal -> terminal));
  }

  public Optional<Terminal> findById(long id) {
    return Optional.ofNullable(terminals.get(id));
  }

  public List<Terminal> findAll() {
    return new ArrayList<>(terminals.values());
  }

}
