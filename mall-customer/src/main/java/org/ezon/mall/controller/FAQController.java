package org.ezon.mall.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ezon.mall.Pagination;
import org.ezon.mall.dto.FAQRequestDTO;
import org.ezon.mall.dto.FAQResponseDTO;
import org.ezon.mall.exception.FAQErrorCode;
import org.ezon.mall.exception.FAQException;
import org.ezon.mall.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/faq")
public class FAQController {
	
	@Autowired
	private FAQService faqService;

	public FAQController(FAQService faqService) {
		this.faqService = faqService;
	}
	
	// FAQ 전체 조회
	@GetMapping
	public ResponseEntity<Map<String,Object>> getAllFAQ(
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int perPage) {
		Map<String, Object> result = new HashMap<>();
		List<FAQResponseDTO> faqList = faqService.getAllFAQ();
		result.put("faqList", Pagination.paging(faqList, perPage, page));
		result.put("totalPage", Pagination.totalPage(faqList, perPage));
		return ResponseEntity.ok(result);
	}
	
	// FAQ 등록
	@PostMapping
	public ResponseEntity<FAQResponseDTO> createFAQ(@RequestBody FAQRequestDTO dto) {
		FAQResponseDTO saved = faqService.createFAQ(dto);
		return ResponseEntity.ok(saved);
	}
	
	// FAQ 수정
	@PutMapping("/{faqId}")
	public ResponseEntity<FAQResponseDTO> updateFAQ(@PathVariable Long faqId,
			@RequestBody FAQRequestDTO dto) {
        FAQResponseDTO updated = faqService.updateFAQ(faqId, dto);
        return ResponseEntity.ok(updated);
	}
	
    // FAQ 삭제 
    @DeleteMapping("/{faqId}")
    public ResponseEntity<Void> deleteFAQ(@PathVariable Long faqId) {
        faqService.deleteFAQ(faqId);
        return ResponseEntity.ok().build();
    }
}
