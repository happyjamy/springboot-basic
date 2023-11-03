package com.programmers.springbootbasic.presentation;

import static com.programmers.springbootbasic.exception.ErrorCode.INVALID_MENU;

import com.programmers.springbootbasic.exception.exceptionClass.CustomException;
import com.programmers.springbootbasic.mediator.ConsoleRequest;
import com.programmers.springbootbasic.mediator.ConsoleResponse;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MainMenu {
    EXIT("exit", ControllerAdapter::handleExit),
    CREATE_VOUCHER("create voucher", ControllerAdapter::createVoucher),
    FIND_ALL_VOUCHER("list voucher", ControllerAdapter::getAllVouchers),
    FIND_VOUCHER("find voucher", ControllerAdapter::getVoucherById),
    DELETE_VOUCHER("delete voucher", ControllerAdapter::deleteVoucherById),
    UPDATE_VOUCHER("update voucher", ControllerAdapter::updateVoucher),
    CREATE_USER("register user", ControllerAdapter::createUser),
    LIST_BLACK_USER("list black user", ControllerAdapter::getBlackList),
    CREATE_USER_VOUCHER("register my voucher", ControllerAdapter::createUserVoucher),
    FIND_USER_BY_VOUCHER("find users by voucher Id", ControllerAdapter::findUserByVoucherId),
    FIND_VOUCHER_MINE("find my voucher", ControllerAdapter::findVoucherByUserNickname),
    DELETE_VOUCHER_MINE("delete my voucher", ControllerAdapter::deleteUserVoucherById),
    ;

    private final String command;
    private final BiFunction<ControllerAdapter, Object[], ConsoleResponse> function;
    private static final Map<String, MainMenu> map = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(MainMenu::getCommand, Function.identity())));

    MainMenu(String command, BiFunction<ControllerAdapter, Object[], ConsoleResponse> function) {
        this.command = command;
        this.function = function;
    }

    public ConsoleResponse execute(ControllerAdapter controller, Object... params) {
        return function.apply(controller, params);
    }

    public static ConsoleResponse routeToController(
        ConsoleRequest req,
        ControllerAdapter controllerAdapter
    ) {
        var result = map.get(req.getCommand());
        if (result != null) {
            if (req.getBody().isPresent()) {
                return result.execute(controllerAdapter, req.getBody().get());
            } else {
                return result.execute(controllerAdapter);
            }
        }

        throw new CustomException(INVALID_MENU);
    }

    public String getCommand() {
        return command;
    }

}
