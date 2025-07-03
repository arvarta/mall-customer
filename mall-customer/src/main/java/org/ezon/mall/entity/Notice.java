package org.ezon.mall.entity;

import java.time.LocalDateTime;

import org.ezon.mall.status.NoticeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "notice")
public class Notice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notice_id")
	private Long noticeId;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "content")
	private String content;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private NoticeType type;
	
	@Column(name = "view_count")
	private int viewCount = 0;
	
	@Column(name = "attachment")
	private String attachment;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();
	
}
