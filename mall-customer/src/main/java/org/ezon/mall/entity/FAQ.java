package org.ezon.mall.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "faq")
public class FAQ {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "faq_id")
	private Long faqId;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "faq_title")
	private String faqTitle;
	
	@Column(name = "faq_content")
	private String faqContent;
	
	@Column(name = "status", nullable = false)
	private Boolean status;
}
