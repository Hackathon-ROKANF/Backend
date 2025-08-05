package com.springboot.knockbackend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.knockbackend.exception.RealEstateApiException;
import com.springboot.knockbackend.properties.CodefProperties;
import com.springboot.knockbackend.service.RealEstateService;
import com.springboot.knockbackend.util.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class RealEstateServiceImpl implements RealEstateService {

    private static final Logger logger = LoggerFactory.getLogger(RealEstateServiceImpl.class);

    private final CodefProperties codefProperties;
    private final RSAUtil rsaUtil;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public RealEstateServiceImpl(CodefProperties codefProperties, RSAUtil rsaUtil) {
        this.codefProperties = codefProperties;
        this.rsaUtil = rsaUtil;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Map<String, Object> simpleSearch(String phoneNo, String password, String address) {
        try {
            String encryptedPassword = rsaUtil.encryptPassword(password, codefProperties.getPublicKeyPem());

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("organization", "0002");
            requestBody.put("phoneNo", phoneNo);
            requestBody.put("password", encryptedPassword);
            requestBody.put("inquiryType", "1");
            requestBody.put("address", address);
            requestBody.put("realtyType", "1");
            requestBody.put("issueType", "2");
            requestBody.put("jointMortgageJeonseYN", "1");
            requestBody.put("tradingYN", "1");
            requestBody.put("registerSummaryYN", "1");

            return callCodefApi(requestBody);
        } catch (Exception e) {
            logger.error("간편검색 오류: ", e);
            throw new RealEstateApiException("간편검색 실패: " + e.getMessage());
        }
    }

    @Override
    public Map<String, Object> searchByLotNumber(String phoneNo, String password, String addrSido,
                                                  String addrDong, String addrLotNumber, String realtyType,
                                                  String buildingName, String dong, String ho) {
        try {
            String encryptedPassword = rsaUtil.encryptPassword(password, codefProperties.getPublicKeyPem());

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("organization", "0002");
            requestBody.put("phoneNo", phoneNo);
            requestBody.put("password", encryptedPassword);
            requestBody.put("inquiryType", "2");
            requestBody.put("realtyType", realtyType != null ? realtyType : "1");
            requestBody.put("addr_sido", addrSido);
            requestBody.put("addr_dong", addrDong);
            requestBody.put("addr_lotNumber", addrLotNumber);
            requestBody.put("buildingName", buildingName);
            requestBody.put("dong", dong);
            requestBody.put("ho", ho);
            requestBody.put("inputSelect", "0");
            requestBody.put("issueType", "2");
            requestBody.put("jointMortgageJeonseYN", "1");
            requestBody.put("tradingYN", "1");
            requestBody.put("registerSummaryYN", "1");

            return callCodefApi(requestBody);
        } catch (Exception e) {
            logger.error("지번검색 오류: ", e);
            throw new RealEstateApiException("지번검색 실패: " + e.getMessage());
        }
    }

    @Override
    public Map<String, Object> searchByRoadAddress(String phoneNo, String password, String addrSido,
                                                    String addrSigungu, String addrRoadName, String addrBuildingNumber,
                                                    String buildingName, String dong, String ho) {
        try {
            String encryptedPassword = rsaUtil.encryptPassword(password, codefProperties.getPublicKeyPem());

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("organization", "0002");
            requestBody.put("phoneNo", phoneNo);
            requestBody.put("password", encryptedPassword);
            requestBody.put("inquiryType", "3");
            requestBody.put("realtyType", "1");
            requestBody.put("addr_sido", addrSido);
            requestBody.put("addr_sigungu", addrSigungu);
            requestBody.put("addr_roadName", addrRoadName);
            requestBody.put("addr_buildingNumber", addrBuildingNumber);
            requestBody.put("buildingName", buildingName);
            requestBody.put("dong", dong);
            requestBody.put("ho", ho);
            requestBody.put("issueType", "2");
            requestBody.put("jointMortgageJeonseYN", "1");
            requestBody.put("tradingYN", "1");
            requestBody.put("registerSummaryYN", "1");

            return callCodefApi(requestBody);
        } catch (Exception e) {
            logger.error("도로명주소검색 오류: ", e);
            throw new RealEstateApiException("도로명주소검색 실패: " + e.getMessage());
        }
    }

    private Map<String, Object> callCodefApi(Map<String, Object> requestBody) {
        try {
            // OAuth 토큰 획득
            String accessToken = getAccessToken();

            // API 호출
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            logger.info("CODEF API 호출 시작 - URL: {}", codefProperties.getBaseUrl());
            logger.info("요청 데이터: {}", objectMapper.writeValueAsString(requestBody));

            ResponseEntity<String> response = restTemplate.exchange(
                codefProperties.getBaseUrl(),
                HttpMethod.POST,
                entity,
                String.class
            );

            logger.info("CODEF API 응답 상태: {}", response.getStatusCode());
            logger.info("CODEF API 응답 헤더: {}", response.getHeaders());
            logger.info("CODEF API 원시 응답: {}", response.getBody());

            // 응답이 JSON인지 확인
            String responseBody = response.getBody();
            if (responseBody == null || responseBody.trim().isEmpty()) {
                throw new RuntimeException("API 응답이 비어있습니다");
            }

            // URL 디코딩 처리
            String decodedResponse = responseBody;
            try {
                if (responseBody.contains("%")) {
                    decodedResponse = java.net.URLDecoder.decode(responseBody, "UTF-8");
                    logger.info("URL 디코딩된 응답: {}", decodedResponse);
                }
            } catch (Exception e) {
                logger.warn("URL 디코딩 실패, 원본 응답 사용: {}", e.getMessage());
            }

            // HTML 응답인지 확인 (오류 페이지)
            if (decodedResponse.trim().startsWith("<!DOCTYPE") || decodedResponse.trim().startsWith("<html")) {
                throw new RuntimeException("API에서 HTML 오류 페이지를 반환했습니다: " + decodedResponse.substring(0, Math.min(200, decodedResponse.length())));
            }

            // JSON 파싱 시도
            Map<String, Object> apiResponse;
            try {
                apiResponse = objectMapper.readValue(decodedResponse, Map.class);
                logger.info("파싱된 API 응답: {}", apiResponse);
            } catch (Exception e) {
                logger.error("JSON 파싱 실패. 원본: {}", responseBody);
                logger.error("디코딩된 응답: {}", decodedResponse);
                throw new RuntimeException("API 응답을 JSON으로 파싱할 수 없습니다: " + decodedResponse.substring(0, Math.min(200, decodedResponse.length())));
            }

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("data", apiResponse);
            return result;

        } catch (Exception e) {
            logger.error("CODEF API 호출 실패: ", e);
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("success", false);
            errorResult.put("error", "API 호출 실패");
            errorResult.put("message", e.getMessage());
            errorResult.put("details", e.getClass().getSimpleName());
            return errorResult;
        }
    }

    private String getAccessToken() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBasicAuth(codefProperties.getClientId(), codefProperties.getClientSecret());

            String body = "grant_type=client_credentials&scope=read";
            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            logger.info("OAuth 토큰 요청 시작");

            ResponseEntity<Map> response = restTemplate.exchange(
                codefProperties.getOauthUrl(),
                HttpMethod.POST,
                entity,
                Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            String accessToken = (String) responseBody.get("access_token");

            logger.info("OAuth 토큰 획득 성공");
            return accessToken;

        } catch (Exception e) {
            logger.error("OAuth 토큰 획득 실패: ", e);
            throw new RealEstateApiException("OAuth 토큰 획득 실패: " + e.getMessage());
        }
    }
}