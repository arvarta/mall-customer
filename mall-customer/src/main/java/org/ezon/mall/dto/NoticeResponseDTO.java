package org.ezon.mall.dto;

import java.time.LocalDateTime;

import org.ezon.mall.status.NoticeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeResponseDTO {
	private Long noticeId;
	private String title;
	private String content;
	private NoticeType type;
	private int viewCount;
	private String attachment;
	private LocalDateTime createdAt;
	
}
