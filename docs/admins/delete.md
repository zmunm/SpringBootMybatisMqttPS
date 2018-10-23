# 관리자 삭제

**URL** : `/admins`

**Method** : `DELETE`

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
```
## Notes

* 실 삭제가 아닌 플래그 수정