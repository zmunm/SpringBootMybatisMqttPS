# 관리자 정보 가져오기

**URL** : `/admins`

**Method** : `GET`

## Parameter
**required**

Method Type|Name|Data Type|Explain
---|---|---|---
RequestParam|id|String|관리자 아이디
RequestParam|password|String|관리자 비밀번호 (SHA-256)

**optional**

Method Type|Name|Data Type|Explain
---|---|---|---
|||

## Success Response

**Code** : `200 OK`

**Data examples**
```json
{
    "id": "testId",
    "password": "$2a$10$dYZpr/BNP7FtIG8yF3I2gu1ztohJO1qgIP3T0Xj3VYmz9Fw9N8fsy"
}
```
**Content examples**
```json
{
    "id": "testId",
    "password": "$2a$10$dYZpr/BNP7FtIG8yF3I2gu1ztohJO1qgIP3T0Xj3VYmz9Fw9N8fsy",
    "name": "AdminTest",
    "email": "test@test.tt"
}
```
## Notes

* 로그인 시 사용