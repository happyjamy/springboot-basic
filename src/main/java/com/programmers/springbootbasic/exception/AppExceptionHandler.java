package com.programmers.springbootbasic.exception;

import static com.programmers.springbootbasic.exception.ErrorCode.DATABASE_ERROR;

import com.programmers.springbootbasic.exception.exceptionClass.CustomException;
import com.programmers.springbootbasic.exception.exceptionClass.FileIOException;
import com.programmers.springbootbasic.exception.exceptionClass.SystemException;
import com.programmers.springbootbasic.exception.exceptionClass.UserException;
import com.programmers.springbootbasic.exception.exceptionClass.VoucherException;
import com.programmers.springbootbasic.mediator.ConsoleResponse;
import com.programmers.springbootbasic.mediator.RequestProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppExceptionHandler {

    private final RequestProcessor requestProcessor;
    private final ConfigurableApplicationContext context;

    public AppExceptionHandler(
        RequestProcessor requestProcessor,
        ConfigurableApplicationContext context
    ) {
        this.requestProcessor = requestProcessor;
        this.context = context;
    }

    public void handle() {
        try {
            requestProcessor.run();
        } catch (SystemException e) {
            exceptionHandler("System Error : %s", e.getMessage(), e.getMessage());
            handleExit(e);
        } catch (FileIOException e) {
            exceptionHandler("FileIO Error : %s", e.getMessage(), e.getMessage());
        } catch (CustomException e) {
            exceptionHandler("Custom Error : %s", e.getMessage(), e.getMessage());
        } catch (VoucherException e) {
            exceptionHandler("Voucher Error : %s", e.getMessage(), e.getMessage());
        } catch (UserException e) {
            exceptionHandler("User Error : %s", e.getMessage(), e.getMessage());
        } catch (DataAccessException e) {
            exceptionHandler("DataAccess Error : %s", e.getMessage(), DATABASE_ERROR.getMessage());
        } catch (Exception e) {
            exceptionHandler("Unknown Error : %s", e.getMessage(), e.getMessage());
        }
    }
    private void exceptionHandler(String format, String logMessage, String responseMessage) {
        log.error(String.format(format, logMessage));
        requestProcessor.sendResponse(
            ConsoleResponse.createNoBodyResponse(responseMessage)
        );
    }

    private void handleExit(SystemException e) {
        if (e.getErrorCode().equals(ErrorCode.EXIT)) {
            context.close();
        }
    }
}
