package com.springboot.knockbackend.service;

import java.util.Map;

public interface RealEstateService {
    Map<String, Object> simpleSearch(String phoneNo, String password, String address);

    // Python 코드와 동일한 검색 메서드들 추가
    Map<String, Object> searchByLotNumber(String phoneNo, String password,
            String addrSido, String addrDong, String addrLotNumber,
            String realtyType, String buildingName, String dong, String ho);

    Map<String, Object> searchByRoadAddress(String phoneNo, String password,
            String addrSido, String addrSigungu, String addrRoadName, String addrBuildingNumber,
            String buildingName, String dong, String ho);
}