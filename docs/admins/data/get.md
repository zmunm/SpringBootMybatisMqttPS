# 관리자 환경정보 가져오기

**URL** : `/admins/data`

**Method** : `GET`

## Parameter
**required**

Method Type|Name|Data Type|Explain
---|---|---|---
RequestParam|id|String|관리자 아이디

**optional**

Method Type|Name|Data Type|Explain
---|---|---|---
|||

## Success Response

**Code** : `200 OK`

**Data examples**
```json
{
    "id": "testId"
}
```
**Content examples**
```json
{
    "dust":215,
    "co2":214,
    "lux":200,
    "temp":null,
    "humi":19
}
```
## Notes