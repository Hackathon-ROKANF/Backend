# 부동산 등기부등본 조회 API - POST 요청 예시
테스트 페이지: http://localhost:8080/api-test.html
## 1. 간편검색 (Simple Search)
```bash
curl -X POST http://localhost:8080/api/real-estate/simple-search \
  -H "Content-Type: application/json" \
  -d '{
    "phoneNo": "01083662414",
    "password": "0000",
    "address": "경기도 용인시"
  }'
```

## 2. 지번검색 (Lot Number Search)
```bash
curl -X POST http://localhost:8080/api/real-estate/lot-number-search \
  -H "Content-Type: application/json" \
  -d '{
    "phoneNo": "01083662414",
    "password": "0000",
    "addrSido": "경기도",
    "addrDong": "지행동",
    "addrLotNumber": "691-3",
    "realtyType": "1",
    "buildingName": "현진에버빌",
    "dong": "104",
    "ho": "901"
  }'
```

## 3. 도로명주소 검색 (Road Address Search)
```bash
curl -X POST http://localhost:8080/api/real-estate/road-address-search \
  -H "Content-Type: application/json" \
  -d '{
    "phoneNo": "01083662414",
    "password": "0000",
    "addrSido": "경기도",
    "addrSigungu": "용인시",
    "addrRoadName": "신갈로84번길",
    "addrBuildingNumber": "16-9",
    "buildingName": "대광타운",
    "dong": "",
    "ho": "202"
  }'
```

## Python requests 예시

### 1. 간편검색
```python
import requests
import json

url = "http://localhost:8080/api/real-estate/simple-search"
data = {
    "phoneNo": "01083662414",
    "password": "0000",
    "address": "경기도 용인시"
}

response = requests.post(url, json=data)
print("Status Code:", response.status_code)
print("Response:", json.dumps(response.json(), indent=2, ensure_ascii=False))
```

### 2. 지번검색
```python
import requests
import json

url = "http://localhost:8080/api/real-estate/lot-number-search"
data = {
    "phoneNo": "01083662414",
    "password": "0000",
    "addrSido": "경기도",
    "addrDong": "지행동",
    "addrLotNumber": "691-3",
    "realtyType": "1",
    "buildingName": "현진에버빌",
    "dong": "104",
    "ho": "901"
}

response = requests.post(url, json=data)
print("Status Code:", response.status_code)
print("Response:", json.dumps(response.json(), indent=2, ensure_ascii=False))
```

### 3. 도로명주소 검색
```python
import requests
import json

url = "http://localhost:8080/api/real-estate/road-address-search"
data = {
    "phoneNo": "01083662414",
    "password": "0000",
    "addrSido": "경기도",
    "addrSigungu": "용인시",
    "addrRoadName": "신갈로84번길",
    "addrBuildingNumber": "16-9",
    "buildingName": "대광타운",
    "dong": "",
    "ho": "202"
}

response = requests.post(url, json=data)
print("Status Code:", response.status_code)
print("Response:", json.dumps(response.json(), indent=2, ensure_ascii=False))
```

## JavaScript fetch 예시

### 1. 간편검색
```javascript
const simpleSearch = async () => {
    const response = await fetch('http://localhost:8080/api/real-estate/simple-search', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            phoneNo: "01083662414",
            password: "0000",
            address: "경기도 용인시"
        })
    });
    
    const result = await response.json();
    console.log('결과:', result);
};
```

### 2. 지번검색
```javascript
const lotNumberSearch = async () => {
    const response = await fetch('http://localhost:8080/api/real-estate/lot-number-search', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            phoneNo: "01083662414",
            password: "0000",
            addrSido: "경기도",
            addrDong: "지행동",
            addrLotNumber: "691-3",
            realtyType: "1",
            buildingName: "현진에버빌",
            dong: "104",
            ho: "901"
        })
    });
    
    const result = await response.json();
    console.log('결과:', result);
};
```

### 3. 도로명주소 검색
```javascript
const roadAddressSearch = async () => {
    const response = await fetch('http://localhost:8080/api/real-estate/road-address-search', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            phoneNo: "01083662414",
            password: "0000",
            addrSido: "경기도",
            addrSigungu: "용인시",
            addrRoadName: "신갈로84번길",
            addrBuildingNumber: "16-9",
            buildingName: "대광타운",
            dong: "",
            ho: "202"
        })
    });
    
    const result = await response.json();
    console.log('결과:', result);
};
```

## Postman 설정

### 1. 간편검색
- Method: POST
- URL: http://localhost:8080/api/real-estate/simple-search
- Headers: Content-Type: application/json
- Body (raw JSON):
```json
{
    "phoneNo": "01083662414",
    "password": "0000",
    "address": "경기도 용인시"
}
```

### 2. 지번검색
- Method: POST
- URL: http://localhost:8080/api/real-estate/lot-number-search
- Headers: Content-Type: application/json
- Body (raw JSON):
```json
{
    "phoneNo": "01083662414",
    "password": "0000",
    "addrSido": "경기도",
    "addrDong": "지행동",
    "addrLotNumber": "691-3",
    "realtyType": "1",
    "buildingName": "현진에버빌",
    "dong": "104",
    "ho": "901"
}
```

### 3. 도로명주소 검색
- Method: POST
- URL: http://localhost:8080/api/real-estate/road-address-search
- Headers: Content-Type: application/json
- Body (raw JSON):
```json
{
    "phoneNo": "01083662414",
    "password": "0000",
    "addrSido": "경기도",
    "addrSigungu": "용인시",
    "addrRoadName": "신갈로84번길",
    "addrBuildingNumber": "16-9",
    "buildingName": "대광타운",
    "dong": "",
    "ho": "202"
}
```

## 응답 예시

### 성공 응답 (CF-13006 - 검색결과 없음)
```json
{
    "result": {
        "code": "CF-13006",
        "extraMessage": "",
        "message": "검색결과가 없습니다. 검색어에 잘못된 철자가 없는지, 정확한 주소인지 다시 한번 확인해 주세요",
        "transactionId": "68914f41ec82188312ac8a3c"
    },
    "data": {}
}
```

### 성공 응답 (CF-00000 - 조회 성공)
```json
{
    "result": {
        "code": "CF-00000",
        "extraMessage": "",
        "message": "정상처리되었습니다.",
        "transactionId": "68914f41ec82188312ac8a3c"
    },
    "data": {
        "registrations": [
            {
                "uniqueNo": "1234567890123456",
                "address": "경기도 용인시 기흥구 지행동 691-3",
                "buildingName": "현진에버빌",
                "dong": "104",
                "ho": "901"
            }
        ]
    }
}
```

### 오류 응답 (CF-04020 - 비밀번호 복호화 오류)
```json
{
    "result": {
        "code": "CF-04020",
        "extraMessage": "",
        "message": "비밀번호 복호화에 문제가 발생했습니다. 요청 파라미터 중 비밀번호 항목의 암호화 여부를 확인하세요.",
        "transactionId": "68914f41ec82188312ac8a3c"
    },
    "data": {}
}
```

## 사용 시 주의사항

1. **실제 데이터 사용**: 테스트용 더미 데이터가 아닌 실제 휴대폰 번호와 비밀번호를 사용해야 합니다.
2. **비밀번호**: 4자리 숫자로 사용자가 임의로 설정한 값입니다.
3. **서버 실행**: Spring Boot 애플리케이션이 localhost:8080에서 실행 중이어야 합니다.
4. **CORS 설정**: 웹 브라우저에서 테스트할 경우 CORS 설정이 필요할 수 있습니다.
