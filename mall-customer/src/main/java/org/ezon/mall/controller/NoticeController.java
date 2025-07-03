package org.ezon.mall.controller;

import org.ezon.mall.dto.NoticeRequestDTO;
import org.ezon.mall.dto.NoticeResponseDTO;
import org.ezon.mall.exception.NoticeErrorCode;
import org.ezon.mall.exception.NoticeException;
import org.ezon.mall.service.NoticeService;
import org.ezon.mall.status.NoticeType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer/notice")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 공지사항 전체 목록 (Map으로 감싸서 페이징 포함 반환)
    @GetMapping
    public ResponseEntity<Map<String, Object>> getNotices(
            @RequestParam NoticeType type,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    ) {
        Map<String, Object> result;

        if (type == NoticeType.ADMIN) {
            // 전체 공지 조회
            result = noticeService.getAllNotices(page, perPage);
        } else {
            // 타입 필터링 조회 (BUYER or SELLER)
            result = noticeService.getNoticeListByType(type, page, perPage);
        }

        return ResponseEntity.ok(result);
    }

    // 공지사항 상세
    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeResponseDTO> getNotice(@PathVariable Long noticeId) {
        NoticeResponseDTO dto = noticeService.getNoticeDetail(noticeId);
        return ResponseEntity.ok(dto);
    }

    // 공지사항 등록 (관리자만)
    @PostMapping
    public ResponseEntity<?> createNotice(@RequestBody NoticeRequestDTO dto) {
        noticeService.createNotice(dto);
        return ResponseEntity.ok().build();
    }

    // 공지사항 수정 (관리자만)
    @PutMapping("/{noticeId}")
    public ResponseEntity<?> updateNotice(@PathVariable Long noticeId, @RequestBody NoticeRequestDTO dto) {
        noticeService.updateNotice(noticeId, dto);
        return ResponseEntity.ok().build();
    }

    // 공지사항 삭제 (관리자만)
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return ResponseEntity.ok().build();
    }
}
