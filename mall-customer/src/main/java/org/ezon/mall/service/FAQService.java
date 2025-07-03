package org.ezon.mall.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.ezon.mall.dto.FAQRequestDTO;
import org.ezon.mall.dto.FAQResponseDTO;
import org.ezon.mall.entity.FAQ;
import org.ezon.mall.exception.FAQErrorCode;
import org.ezon.mall.exception.FAQException;
import org.ezon.mall.repository.FAQRepository;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.java.Log;

@Service
public class FAQService {

	@Autowired
	private FAQRepository faqRepository;

	public FAQService(FAQRepository faqRepository) {
		this.faqRepository = faqRepository;
	}
	
	// 전체 FAQ 조회
    @Transactional(readOnly = true)
    public List<FAQResponseDTO> getAllFAQ() {
        List<FAQ> faqs = faqRepository.findByOrderByFaqIdDesc();
        List<FAQResponseDTO> result = new ArrayList<>();
        for (FAQ faq : faqs) {
            if (faq.getStatus() != null && faq.getStatus()) {
                result.add(toResponseDTO(faq));
            }
        }
        return result;
    }
	
	// FAQ 등록
	@Transactional
	public FAQResponseDTO createFAQ(FAQRequestDTO dto) {
		FAQ faq = null;
		try {
			if(dto.getFaqTitle() == null || dto.getFaqTitle().trim().isEmpty() || 
				dto.getFaqContent() == null || dto.getFaqContent().trim().isEmpty()) {
				throw new FAQException("질문/답변은 비워둘 수 없습니다.", FAQErrorCode.INVALID_REQUEST);
			}
			faq = new FAQ();
			faq.setFaqTitle(dto.getFaqTitle());
			faq.setFaqContent(dto.getFaqContent());
			faq.setStatus(dto.getStatus());
			faq.setUserId(dto.getUserId());
			faq = faqRepository.save(faq);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toResponseDTO(faq);
	}
	
	// FAQ 수정
	@Transactional
	public FAQResponseDTO updateFAQ(Long faqId, FAQRequestDTO dto) {
        Optional<FAQ> optional = faqRepository.findById(faqId);
        if (!optional.isPresent()) {
            throw new FAQException("FAQ가 존재하지 않습니다.", FAQErrorCode.NOT_FOUND);
        }
        
        FAQ faq = optional.get();
		if(dto.getFaqTitle() == null || dto.getFaqTitle().trim().isEmpty() || 
			dto.getFaqContent() == null || dto.getFaqContent().trim().isEmpty()) {
			throw new FAQException("질문/답변은 비워둘 수 없습니다.", FAQErrorCode.INVALID_REQUEST);
		}
	
		faq.setFaqTitle(dto.getFaqTitle());
		faq.setFaqContent(dto.getFaqContent());
		faq.setStatus(dto.getStatus());
		FAQ saved = faqRepository.save(faq);
		return toResponseDTO(saved);
	}

	// FAQ 삭제
	@Transactional
	public void deleteFAQ(Long faqId) {
		if(!faqRepository.existsById(faqId)) {
			throw new FAQException("FAQ가 존재하지 않습니다.", FAQErrorCode.NOT_FOUND);
		}
		faqRepository.deleteById(faqId);
	}
	
	private FAQResponseDTO toResponseDTO(FAQ faq) {
		FAQResponseDTO dto = new FAQResponseDTO();
		dto.setFaqId(faq.getFaqId());
		dto.setFaqTitle(faq.getFaqTitle());
		dto.setFaqContent(faq.getFaqContent());
		dto.setStatus(faq.getStatus());
		dto.setUserId(faq.getUserId());
		return dto;
	}
	
}
