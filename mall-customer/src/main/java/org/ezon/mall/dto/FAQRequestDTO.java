package org.ezon.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FAQRequestDTO {
	private String faqTitle;
	private String faqContent;
	private Boolean status;
	private Long userId;
}
