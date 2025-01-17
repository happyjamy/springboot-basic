package com.programmers.springbootbasic.mediator.requestGenerator;

import com.programmers.springbootbasic.mediator.ConsoleRequest;
import com.programmers.springbootbasic.presentation.MainMenu;
import org.springframework.stereotype.Component;

@Component
public class ExitRequestGenerator implements MenuRequestGenerator {

    @Override
    public String getMenuCommand() {
        return MainMenu.EXIT.getCommand();
    }

    @Override
    public ConsoleRequest<Void> generateRequest() {
        return new ConsoleRequest<>(getMenuCommand());
    }
}
