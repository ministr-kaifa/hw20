package ru.zubkoff.sber.hw20.restterminal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ru.zubkoff.sber.hw20.restterminal.service.TerminalService;

@RestController
public class TerminalController {

  private final TerminalService terminalService;

  public TerminalController(TerminalService terminalService) {
    this.terminalService = terminalService;
  }

  @GetMapping("/api/terminals")
  public List<TerminalDto> getTerminals() {
    return terminalService.findAll().stream()
        .map(TerminalDto::fromEntity)
        .toList();
  }

  @GetMapping("/api/terminals/{terminalId}")
  public TerminalDto getTerminals(@PathVariable long terminalId) {
    return terminalService.findById(terminalId)
        .map(TerminalDto::fromEntity)
        .orElseThrow(() -> {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
  }

  @PutMapping("/api/terminals/{terminalId}/pin")
  public void setPin(@PathVariable Long terminalId, @RequestParam String value) {
    terminalService.findById(terminalId)
        .orElseThrow(() -> {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        })
        .login(value);
  }

  @GetMapping("/api/terminals/{terminalId}/balance")
  public Double getBalance(@PathVariable Long terminalId) {
    return terminalService.findById(terminalId)
        .orElseThrow(() -> {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        })
        .getBalance();
  }

  @PostMapping("/api/terminals/{terminalId}/balance")
  public void updateBalance(@PathVariable Long terminalId, @RequestParam Double diff) {
    terminalService.findById(terminalId)
        .orElseThrow(() -> {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        })
        .updateBalance(diff);
  }

}
