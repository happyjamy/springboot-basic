package com.programmers.springbootbasic.mediator.requestGenerator;

import com.programmers.springbootbasic.mediator.ConsoleRequest;

public interface MenuRequestGenerator<T> {

    String getMenuCommand();

    ConsoleRequest<T> generateRequest();
}
