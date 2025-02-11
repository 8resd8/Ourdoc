package com.ssafy.ourdoc.domain.debate.dto.openvidu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {
	private String sessionName;
	private String nickname;
}
