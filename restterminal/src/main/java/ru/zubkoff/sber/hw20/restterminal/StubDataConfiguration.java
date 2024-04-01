package ru.zubkoff.sber.hw20.restterminal;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.zubkoff.sber.hw20.restterminal.domain.server.Account;
import ru.zubkoff.sber.hw20.restterminal.domain.server.TerminalServer;
import ru.zubkoff.sber.hw20.restterminal.domain.terminal.RegexPinValidator;
import ru.zubkoff.sber.hw20.restterminal.domain.terminal.Terminal;

@Configuration
public class StubDataConfiguration {
  @Bean
  List<Terminal> terminals(TerminalServer server) {
    var regexPinValidator = new RegexPinValidator();
    return List.of(
        new Terminal(1, 4, regexPinValidator, server),
        new Terminal(2, 3, regexPinValidator, server),
        new Terminal(3, 2, regexPinValidator, server),
        new Terminal(4, 1, regexPinValidator, server));
  }

  @Bean
  List<Account> accounts() {
    return List.of(
        new Account(1, "1111", 10_000),
        new Account(2, "2222", 10_000),
        new Account(3, "3333", 10_000),
        new Account(4, "4444", 10_000));
  }
}
