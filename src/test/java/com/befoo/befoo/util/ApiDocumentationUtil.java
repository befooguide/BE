package com.befoo.befoo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.ResultHandler;

/**
 * API 문서화 관련 유틸리티 클래스
 * 테스트 코드에서 API 요청과 응답을 문서화하는 데 사용됩니다.
 */
public class ApiDocumentationUtil {

    private static final Logger logger = LoggerFactory.getLogger(ApiDocumentationUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private ApiDocumentationUtil() {
        // 유틸리티 클래스이므로 인스턴스화 방지
    }

    /**
     * API 요청을 문서화하는 핸들러
     * @return ResultHandler 인스턴스
     */
    public static ResultHandler documentRequest() {
        return result -> {
            try {
                MockHttpServletRequest request = result.getRequest();
                StringBuilder apiDoc = new StringBuilder();
                
                // 기본 정보 섹션 생성
                formatBasicInfo(apiDoc, request);
                
                // 요청 본문이 있는 경우 처리
                if (request.getContentAsByteArray() != null && request.getContentAsByteArray().length > 0) {
                    formatRequestBody(apiDoc, request);
                }
                
                // 명세서 출력
                logger.info(apiDoc.toString());
            } catch (Exception e) {
                logger.error("API 명세서 생성 중 오류 발생: {}", e.getMessage());
            }
        };
    }
    
    /**
     * API 응답을 문서화하는 핸들러
     * @return ResultHandler 인스턴스
     */
    public static ResultHandler documentResponse() {
        return result -> {
            try {
                MockHttpServletResponse response = result.getResponse();
                StringBuilder apiDoc = new StringBuilder();
                
                apiDoc.append("\n### Response Body\n\n");
                
                // 본문 출력
                response.getContentAsByteArray();
                if (response.getContentAsByteArray().length > 0) {
                    String content = response.getContentAsString();
                    String responseContentType = response.getContentType();
                    formatJsonContent(apiDoc, content, responseContentType);
                } else {
                    apiDoc.append("응답 본문이 없습니다.\n");
                }
                
                // 명세서 출력
                logger.info(apiDoc.toString());
            } catch (Exception e) {
                logger.error("API 명세서 생성 중 오류 발생: {}", e.getMessage());
            }
        };
    }
    
    /**
     * 기본 정보 섹션 형식화
     */
    private static void formatBasicInfo(StringBuilder apiDoc, MockHttpServletRequest request) {
        apiDoc.append("\n### **기본 정보**\n\n");
        apiDoc.append("- **URL**: ").append(request.getRequestURI()).append("\n");
        apiDoc.append("- **Method**: ").append(request.getMethod()).append("\n");
        
        // 인증 정보 확인
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && !authHeader.isEmpty()) {
            apiDoc.append("- **인증**: 필요 (JWT 토큰)\n");
        } else {
            apiDoc.append("- **인증**: 불필요\n");
        }
        
        // Content-Type 확인
        String contentType = request.getContentType();
        if (contentType != null && !contentType.isEmpty()) {
            apiDoc.append("- **Content-Type**: ").append(contentType).append("\n");
        }
        
        apiDoc.append("\n---\n");
    }
    
    /**
     * 요청 본문 형식화
     */
    private static void formatRequestBody(StringBuilder apiDoc, MockHttpServletRequest request) {
        String content = new String(request.getContentAsByteArray());
        String contentType = request.getContentType();
        
        apiDoc.append("\n### Request Body\n\n");
        formatJsonContent(apiDoc, content, contentType);
    }
    
    /**
     * JSON 콘텐츠 형식화 (요청/응답 공통)
     */
    private static void formatJsonContent(StringBuilder apiDoc, String content, String contentType) {
        try {
            if (contentType != null && contentType.contains("application/json")) {
                apiDoc.append("```json\n");
                JsonNode jsonNode = objectMapper.readTree(content);
                apiDoc.append(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode));
                apiDoc.append("\n```\n");
            } else {
                apiDoc.append("```\n").append(content).append("\n```\n");
            }
        } catch (JsonProcessingException e) {
            apiDoc.append("```\n").append(content).append("\n```\n");
        }
    }
} 