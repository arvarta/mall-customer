package org.ezon.mall.dto;

import org.ezon.mall.status.NoticeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeRequestDTO {
	private String title;
	private String content;
	private NoticeType type;
	private String attachment;
	private Long userId;
	
}
