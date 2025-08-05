package com.springboot.knockbackend.controller;

import com.springboot.knockbackend.dto.SimpleSearchRequest;
import com.springboot.knockbackend.service.RealEstateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/real-estate")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class RealEstateController {
    private final RealEstateService service;

    public RealEstateController(RealEstateService service) {
        this.service = service;
    }

    @PostMapping("/simple-search")
    public ResponseEntity<Map<String,Object>> simple(@RequestBody SimpleSearchRequest req) {
        try {
            Map<String,Object> result = service.simpleSearch(
                    req.getPhoneNo(),
                    req.getPassword(),
                    req.getAddress()
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "success", false,
                "error", "간편검색 실패",
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/lot-number-search")
    public ResponseEntity<Map<String, Object>> lotNumberSearch(@RequestBody Map<String, String> request) {
        try {
            Map<String, Object> result = service.searchByLotNumber(
                    request.get("phoneNo"),
                    request.get("password"),
                    request.get("addrSido"),
                    request.get("addrDong"),
                    request.get("addrLotNumber"),
                    request.get("realtyType"),
                    request.get("buildingName"),
                    request.get("dong"),
                    request.get("ho")
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "success", false,
                "error", "지번검색 실패",
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/road-address-search")
    public ResponseEntity<Map<String, Object>> roadAddressSearch(@RequestBody Map<String, String> request) {
        try {
            Map<String, Object> result = service.searchByRoadAddress(
                    request.get("phoneNo"),
                    request.get("password"),
                    request.get("addrSido"),
                    request.get("addrSigungu"),
                    request.get("addrRoadName"),
                    request.get("addrBuildingNumber"),
                    request.get("buildingName"),
                    request.get("dong"),
                    request.get("ho")
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "success", false,
                "error", "도로명주소검색 실패",
                "message", e.getMessage()
            ));
        }
    }
}