package com.ssafy.ourdoc.domain.debate.dto.openvidu;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {
	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	private String sessionName;

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	private String nickname;
}
