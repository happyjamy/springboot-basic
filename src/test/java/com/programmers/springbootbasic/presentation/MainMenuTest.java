package com.programmers.springbootbasic.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.programmers.springbootbasic.mediator.ConsoleRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
@TestInstance(Lifecycle.PER_CLASS)
class MainMenuTest {

    private ControllerAdapter controllerAdapter;
    private List<ConsoleRequest> requests;
    private final List<Long> streamPerformances = new ArrayList<>();
    private final List<Long> mapPerformances = new ArrayList<>();

    @BeforeAll
    void setUp() {
        controllerAdapter = mock(ControllerAdapter.class);
        when(controllerAdapter.handleExit(any())).thenReturn(null);
        when(controllerAdapter.createUser(any())).thenReturn(null);
        when(controllerAdapter.createVoucher(any())).thenReturn(null);
        when(controllerAdapter.updateVoucher(any())).thenReturn(null);
        when(controllerAdapter.getAllVouchers(any())).thenReturn(null);
        when(controllerAdapter.getVoucherById(any())).thenReturn(null);
        when(controllerAdapter.deleteVoucherById(any())).thenReturn(null);
        when(controllerAdapter.getBlackList(any())).thenReturn(null);
        when(controllerAdapter.createUserVoucher(any())).thenReturn(null);
        when(controllerAdapter.findUserByVoucherId(any())).thenReturn(null);
        when(controllerAdapter.findVoucherByUserNickname(any())).thenReturn(null);
        when(controllerAdapter.deleteUserVoucherById(any())).thenReturn(null);

        var requests = Stream.of(MainMenu.values())
            .map(menu -> new ConsoleRequest(menu.getCommand())).toList();
        this.requests = requests;
    }

    @Test
    void routeToController2() {
        for (int i = 0; i < 10000; i++) {

            var index = i % MainMenu.values().length;

            long startNanoTime = System.nanoTime();
            MainMenu.routeToController(requests.get(index), controllerAdapter);
            long endNanoTime = System.nanoTime();
            long durationNano = endNanoTime - startNanoTime;

            mapPerformances.add(durationNano);
        }
    }

    @Test
    void routeToController() {
        for (int i = 0; i < 10000; i++) {
            var index = i % MainMenu.values().length;

            long startNanoTime = System.nanoTime();
            MainMenu.routeToController(requests.get(index), controllerAdapter);
            long endNanoTime = System.nanoTime();
            long durationNano = endNanoTime - startNanoTime;

            streamPerformances.add(durationNano);
        }
    }

    @AfterAll
    void showResult() {
        System.out.println("스트림 최고 시간 : " + streamPerformances.stream().max(Long::compareTo).get());
        System.out.println(
            "스트림 평균 시간 : " + streamPerformances.stream().mapToLong(Long::longValue).average()
                .getAsDouble());
        System.out.println();
        System.out.println("맵 최고 시간 : " + mapPerformances.stream().max(Long::compareTo).get());
        System.out.println(
            "맵 평균 시간 : " + mapPerformances.stream().mapToLong(Long::longValue).average()
                .getAsDouble());

    }
}
