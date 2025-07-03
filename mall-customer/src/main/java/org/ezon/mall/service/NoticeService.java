package org.ezon.mall.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.ezon.mall.dto.NoticeRequestDTO;
import org.ezon.mall.dto.NoticeResponseDTO;
import org.ezon.mall.entity.Notice;
import org.ezon.mall.exception.NoticeErrorCode;
import org.ezon.mall.exception.NoticeException;
import org.ezon.mall.repository.NoticeRepository;
import org.ezon.mall.status.NoticeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    // 페이징 리스트 (반드시 Map<String, Object>로 반환)
    @Transactional(readOnly = true)
    public Map<String, Object> getNoticeListByType(NoticeType type, int page, int perPage) {
        List<Notice> allList = noticeRepository.findAll();
        List<Notice> filteredList = new ArrayList<>();
        for (Notice notice : allList) {
            if (notice.getType() == type) {
                filteredList.add(notice);
            }
        }

        // 최신순 정렬 (createdAt 내림차순)
        filteredList.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));

        // 페이징 처리
        int total = filteredList.size();
        int totalPage = total / perPage + ((total % perPage == 0) ? 0 : 1);
        int fromIndex = (page - 1) * perPage;
        int toIndex = Math.min(fromIndex + perPage, total);

        List<NoticeResponseDTO> result = new ArrayList<>();
        if (fromIndex < total) {
            for (int i = fromIndex; i < toIndex; i++) {
                result.add(toResponseDTO(filteredList.get(i)));
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("noticeList", result);
        map.put("totalPage", totalPage);
        map.put("total", total);

        return map;
    }
    
    // 전체 공지사항 조회 (관리자 전용)
    @Transactional(readOnly = true)
    public Map<String, Object> getAllNotices(int page, int perPage) {
        List<Notice> allList = noticeRepository.findAll();

        // 최신순 정렬 (createdAt 내림차순)
        allList.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));

        int total = allList.size();
        int totalPage = total / perPage + ((total % perPage == 0) ? 0 : 1);
        int fromIndex = (page - 1) * perPage;
        int toIndex = Math.min(fromIndex + perPage, total);

        List<NoticeResponseDTO> result = new ArrayList<>();
        if (fromIndex < total) {
            for (int i = fromIndex; i < toIndex; i++) {
                result.add(toResponseDTO(allList.get(i)));
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("noticeList", result);
        map.put("totalPage", totalPage);
        map.put("total", total);

        return map;
    }


    // 상세
    @Transactional(readOnly = true)
    public NoticeResponseDTO getNoticeDetail(Long noticeId) {
        Optional<Notice> optional = noticeRepository.findById(noticeId);
        if (!optional.isPresent()) {
            throw new NoticeException("공지사항을 찾을 수 없습니다.", NoticeErrorCode.NOT_FOUND);
        }
        return toResponseDTO(optional.get());
    }

    // 등록
    @Transactional
    public void createNotice(NoticeRequestDTO dto) {
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty() ||
            dto.getContent() == null || dto.getContent().trim().isEmpty() ||
            dto.getType() == null) {
            throw new NoticeException("제목, 내용, 유형은 필수입니다.", NoticeErrorCode.INVALID_REQUEST);
        }
        Notice notice = new Notice();
        notice.setUserId(dto.getUserId());
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());
        notice.setType(dto.getType());
        notice.setAttachment(dto.getAttachment());
        noticeRepository.save(notice);
    }

    // 수정
    @Transactional
    public void updateNotice(Long noticeId, NoticeRequestDTO dto) {
        Optional<Notice> optional = noticeRepository.findById(noticeId);
        if (!optional.isPresent()) {
            throw new NoticeException("공지사항을 찾을 수 없습니다.", NoticeErrorCode.NOT_FOUND);
        }
        Notice notice = optional.get();
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty() ||
            dto.getContent() == null || dto.getContent().trim().isEmpty() ||
            dto.getType() == null) {
            throw new NoticeException("제목, 내용, 유형은 필수입니다.", NoticeErrorCode.INVALID_REQUEST);
        }
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());
        notice.setType(dto.getType());
        notice.setAttachment(dto.getAttachment());
        noticeRepository.save(notice);
    }

    // 삭제
    @Transactional
    public void deleteNotice(Long noticeId) {
        if (!noticeRepository.existsById(noticeId)) {
            throw new NoticeException("공지사항을 찾을 수 없습니다.", NoticeErrorCode.NOT_FOUND);
        }
        noticeRepository.deleteById(noticeId);
    }

    private NoticeResponseDTO toResponseDTO(Notice notice) {
        NoticeResponseDTO dto = new NoticeResponseDTO();
        dto.setNoticeId(notice.getNoticeId());
        dto.setTitle(notice.getTitle());
        dto.setContent(notice.getContent());
        dto.setType(notice.getType());
        dto.setViewCount(notice.getViewCount());
        dto.setAttachment(notice.getAttachment());
        dto.setCreatedAt(notice.getCreatedAt());
        return dto;
    }
}
