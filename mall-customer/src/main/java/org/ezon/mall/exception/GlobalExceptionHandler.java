package org.ezon.mall.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // FAQ 전용 예외 처리
    @ExceptionHandler(FAQException.class)
    public ResponseEntity<FAQErrorResponse> handleFAQException(FAQException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex.getErrorCode() == FAQErrorCode.NOT_FOUND) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex.getErrorCode() == FAQErrorCode.INVALID_REQUEST) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex.getErrorCode() == FAQErrorCode.FORBIDDEN) {
            status = HttpStatus.FORBIDDEN;
        }
        FAQErrorResponse error = new FAQErrorResponse(false, ex.getMessage(), ex.getErrorCode().name());
        return ResponseEntity.status(status).body(error);
    }

    // Notice 전용 예외 처리
    @ExceptionHandler(NoticeException.class)
    public ResponseEntity<NoticeErrorResponse> handleNoticeException(NoticeException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex.getErrorCode() == NoticeErrorCode.NOT_FOUND) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex.getErrorCode() == NoticeErrorCode.INVALID_REQUEST) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex.getErrorCode() == NoticeErrorCode.FORBIDDEN) {
            status = HttpStatus.FORBIDDEN;
        }
        NoticeErrorResponse error = new NoticeErrorResponse(false, ex.getMessage(), ex.getErrorCode().name());
        return ResponseEntity.status(status).body(error);
    }

    // 그 외 모든 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<NoticeErrorResponse> handleOtherExceptions(Exception ex) {
        ex.printStackTrace();
        NoticeErrorResponse error = new NoticeErrorResponse(false, "서버 오류가 발생했습니다.", NoticeErrorCode.UNKNOWN_ERROR.name());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
