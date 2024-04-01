package ru.zubkoff.sber.hw20.restterminal.exceptions.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FailedBalanceManipulationException extends RuntimeException {

}
