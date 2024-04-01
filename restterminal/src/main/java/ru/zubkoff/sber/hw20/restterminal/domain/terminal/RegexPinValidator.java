package ru.zubkoff.sber.hw20.restterminal.domain.terminal;

public class RegexPinValidator implements PinValidator {

  @Override
  public boolean isValidPin(String pin) {
    return pin.matches("^\\d{4}$");
  }

}
