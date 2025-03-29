package com.befoo.befoo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("전체 애플리케이션 컨텍스트 로드는 시간이 오래 걸리고 불필요한 의존성을 가지므로 비활성화합니다.")
@SpringBootTest
class BefooApplicationTests {

	@Test
	void contextLoads() {
		// 애플리케이션 컨텍스트가 로드되는지 확인하는 테스트
	}

}
