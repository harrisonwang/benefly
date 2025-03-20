# Benefly 公告管理系统 API 文档

本文档详细描述了公告管理系统的 API 接口，包括请求方法、参数和响应格式。

## 目录

1. [获取所有公告](#1-获取所有公告)
2. [获取单个公告](#2-获取单个公告)
3. [创建公告](#3-创建公告)
4. [更新公告](#4-更新公告)
5. [删除公告](#5-删除公告)
6. [发布公告](#6-发布公告)
7. [撤销公告](#7-撤销公告)
8. [调度公告](#8-调度公告)
9. [搜索公告](#9-搜索公告)

---

## 1. 获取所有公告

### 接口功能描述

获取系统中的所有公告列表。

### 接口原型

- 请求方法: GET
- 请求地址: `/api/announcements`
- 传输协议: HTTP

### 请求头

| 参数名 | 参数值 | 是否必须 | 描述 |
| ------ | ------ | -------- | ---- |
| Accept | application/json | 是 | 指定响应的内容类型 |

### 请求参数

无

### 响应头

| 参数名 | 参数值 | 描述 |
| ------ | ------ | ---- |
| Content-Type | application/json | 响应的内容类型 |

### 响应状态码

| 状态码 | 描述 |
| ------ | ---- |
| 200 | 成功 |

### 响应参数

返回公告对象数组，每个公告对象包含以下字段：

| 参数名 | 类型 | 描述 |
| ------ | ---- | ---- |
| id | Long | 公告ID |
| title | String | 公告标题 |
| content | String | 公告内容 |
| status | String | 公告状态（DRAFT, PUBLISHED, REVOKED, EXPIRED） |
| createdBy | String | 创建人 |
| createdAt | String | 创建时间 |
| updatedBy | String | 更新人 |
| updatedAt | String | 更新时间 |
| publishDate | String | 发布时间 |
| expiryDate | String | 过期时间 |

### 请求示例

```bash
curl -X GET http://localhost:8080/api/announcements
```

### 响应示例

```json
[
  {
    "id": 1,
    "title": "Welcome to Benefly",
    "content": "Welcome to the new announcement system. This is a published announcement.",
    "status": "PUBLISHED",
    "createdBy": "admin",
    "createdAt": "2025-03-15T02:56:32.711634",
    "updatedBy": "admin",
    "updatedAt": "2025-03-15T02:56:32.711663",
    "publishDate": "2025-03-15T02:56:32.711671",
    "expiryDate": "2025-04-14T02:56:32.711677"
  },
  {
    "id": 2,
    "title": "Upcoming Features",
    "content": "Stay tuned for exciting new features coming soon! This is a draft announcement.",
    "status": "DRAFT",
    "createdBy": "admin",
    "createdAt": "2025-03-18T02:56:32.748978",
    "updatedBy": "admin",
    "updatedAt": "2025-03-18T02:56:32.74899",
    "publishDate": "2025-03-23T02:56:32.748996",
    "expiryDate": "2025-04-22T02:56:32.749004"
  },
  {
    "id": 3,
    "title": "Past Event",
    "content": "This event has already occurred. This is an expired announcement.",
    "status": "EXPIRED",
    "createdBy": "admin",
    "createdAt": "2025-02-08T02:56:32.750652",
    "updatedBy": "admin",
    "updatedAt": "2025-02-08T02:56:32.750676",
    "publishDate": "2025-02-08T02:56:32.750684",
    "expiryDate": "2025-03-10T02:56:32.750691"
  }
]
```

---

## 2. 获取单个公告

### 接口功能描述

根据ID获取单个公告的详细信息。

### 接口原型

- 请求方法: GET
- 请求地址: `/api/announcements/{id}`
- 传输协议: HTTP

### 请求头

| 参数名 | 参数值 | 是否必须 | 描述 |
| ------ | ------ | -------- | ---- |
| Accept | application/json | 是 | 指定响应的内容类型 |

### 请求参数

| 参数名 | 类型 | 位置 | 是否必须 | 描述 |
| ------ | ---- | ---- | -------- | ---- |
| id | Long | path | 是 | 公告ID |

### 响应头

| 参数名 | 参数值 | 描述 |
| ------ | ------ | ---- |
| Content-Type | application/json | 响应的内容类型 |

### 响应状态码

| 状态码 | 描述 |
| ------ | ---- |
| 200 | 成功 |
| 404 | 公告不存在 |

### 响应参数

返回单个公告对象，包含以下字段：

| 参数名 | 类型 | 描述 |
| ------ | ---- | ---- |
| id | Long | 公告ID |
| title | String | 公告标题 |
| content | String | 公告内容 |
| status | String | 公告状态（DRAFT, PUBLISHED, REVOKED, EXPIRED） |
| createdBy | String | 创建人 |
| createdAt | String | 创建时间 |
| updatedBy | String | 更新人 |
| updatedAt | String | 更新时间 |
| publishDate | String | 发布时间 |
| expiryDate | String | 过期时间 |

### 请求示例

```bash
curl -X GET http://localhost:8080/api/announcements/1
```

### 响应示例

```json
{
  "id": 1,
  "title": "Welcome to Benefly",
  "content": "Welcome to the new announcement system. This is a published announcement.",
  "status": "PUBLISHED",
  "createdBy": "admin",
  "createdAt": "2025-03-15T02:56:32.711634",
  "updatedBy": "admin",
  "updatedAt": "2025-03-15T02:56:32.711663",
  "publishDate": "2025-03-15T02:56:32.711671",
  "expiryDate": "2025-04-14T02:56:32.711677"
}
```

---

## 3. 创建公告

### 接口功能描述

创建一个新的公告。新创建的公告默认状态为草稿（DRAFT）。

### 接口原型

- 请求方法: POST
- 请求地址: `/api/announcements`
- 传输协议: HTTP

### 请求头

| 参数名 | 参数值 | 是否必须 | 描述 |
| ------ | ------ | -------- | ---- |
| Content-Type | application/json | 是 | 指定请求的内容类型 |
| Accept | application/json | 是 | 指定响应的内容类型 |

### 请求参数

| 参数名 | 类型 | 位置 | 是否必须 | 描述 |
| ------ | ---- | ---- | -------- | ---- |
| title | String | body | 是 | 公告标题 |
| content | String | body | 是 | 公告内容 |
| createdBy | String | body | 是 | 创建人 |
| updatedBy | String | body | 是 | 更新人 |
| publishDate | String | body | 否 | 发布时间 |
| expiryDate | String | body | 否 | 过期时间 |

### 响应头

| 参数名 | 参数值 | 描述 |
| ------ | ------ | ---- |
| Content-Type | application/json | 响应的内容类型 |

### 响应状态码

| 状态码 | 描述 |
| ------ | ---- |
| 201 | 创建成功 |
| 400 | 请求参数错误 |

### 响应参数

返回创建的公告对象，包含以下字段：

| 参数名 | 类型 | 描述 |
| ------ | ---- | ---- |
| id | Long | 公告ID |
| title | String | 公告标题 |
| content | String | 公告内容 |
| status | String | 公告状态（DRAFT） |
| createdBy | String | 创建人 |
| createdAt | String | 创建时间 |
| updatedBy | String | 更新人 |
| updatedAt | String | 更新时间 |
| publishDate | String | 发布时间 |
| expiryDate | String | 过期时间 |

### 请求示例

```bash
curl -X POST http://localhost:8080/api/announcements \
  -H "Content-Type: application/json" \
  -d '{"title":"Test Announcement","content":"This is a test announcement","createdBy":"admin","updatedBy":"admin"}'
```

### 响应示例

```json
{
  "id": 4,
  "title": "Test Announcement",
  "content": "This is a test announcement",
  "status": "DRAFT",
  "createdBy": "admin",
  "createdAt": "2025-03-20T02:57:02.514992564",
  "updatedBy": "admin",
  "updatedAt": "2025-03-20T02:57:02.515012231",
  "publishDate": null,
  "expiryDate": null
}
```

---

## 4. 更新公告

### 接口功能描述

更新现有公告的信息。只有处于草稿（DRAFT）或已撤销（REVOKED）状态的公告才能被更新。

### 接口原型

- 请求方法: PUT
- 请求地址: `/api/announcements/{id}`
- 传输协议: HTTP

### 请求头

| 参数名 | 参数值 | 是否必须 | 描述 |
| ------ | ------ | -------- | ---- |
| Content-Type | application/json | 是 | 指定请求的内容类型 |
| Accept | application/json | 是 | 指定响应的内容类型 |

### 请求参数

| 参数名 | 类型 | 位置 | 是否必须 | 描述 |
| ------ | ---- | ---- | -------- | ---- |
| id | Long | path | 是 | 公告ID |
| title | String | body | 是 | 公告标题 |
| content | String | body | 是 | 公告内容 |
| updatedBy | String | body | 是 | 更新人 |
| publishDate | String | body | 否 | 发布时间 |
| expiryDate | String | body | 否 | 过期时间 |

### 响应头

| 参数名 | 参数值 | 描述 |
| ------ | ------ | ---- |
| Content-Type | application/json | 响应的内容类型 |

### 响应状态码

| 状态码 | 描述 |
| ------ | ---- |
| 200 | 更新成功 |
| 400 | 请求参数错误 |
| 404 | 公告不存在 |

### 响应参数

返回更新后的公告对象，包含以下字段：

| 参数名 | 类型 | 描述 |
| ------ | ---- | ---- |
| id | Long | 公告ID |
| title | String | 公告标题 |
| content | String | 公告内容 |
| status | String | 公告状态 |
| createdBy | String | 创建人 |
| createdAt | String | 创建时间 |
| updatedBy | String | 更新人 |
| updatedAt | String | 更新时间 |
| publishDate | String | 发布时间 |
| expiryDate | String | 过期时间 |

### 请求示例

```bash
curl -X PUT http://localhost:8080/api/announcements/4 \
  -H "Content-Type: application/json" \
  -d '{"title":"Updated Announcement","content":"This is an updated announcement","createdBy":"admin","updatedBy":"admin"}'
```

### 响应示例

```json
{
  "id": 4,
  "title": "Updated Announcement",
  "content": "This is an updated announcement",
  "status": "DRAFT",
  "createdBy": "admin",
  "createdAt": "2025-03-20T02:57:02.514993",
  "updatedBy": "admin",
  "updatedAt": "2025-03-20T02:57:02.528364527",
  "publishDate": null,
  "expiryDate": null
}
```

---

## 5. 删除公告

### 接口功能描述

根据ID删除指定的公告。

### 接口原型

- 请求方法: DELETE
- 请求地址: `/api/announcements/{id}`
- 传输协议: HTTP

### 请求头

| 参数名 | 参数值 | 是否必须 | 描述 |
| ------ | ------ | -------- | ---- |
| Accept | application/json | 是 | 指定响应的内容类型 |

### 请求参数

| 参数名 | 类型 | 位置 | 是否必须 | 描述 |
| ------ | ---- | ---- | -------- | ---- |
| id | Long | path | 是 | 公告ID |

### 响应头

无特殊响应头

### 响应状态码

| 状态码 | 描述 |
| ------ | ---- |
| 204 | 删除成功 |
| 404 | 公告不存在 |

### 响应参数

无响应体

### 请求示例

```bash
curl -X DELETE http://localhost:8080/api/announcements/4 -v
```

### 响应示例

```
HTTP/1.1 204 
Date: Thu, 20 Mar 2025 02:57:12 GMT
```

---

## 6. 发布公告

### 接口功能描述

将指定的公告状态更改为已发布（PUBLISHED）。只有处于草稿（DRAFT）或已撤销（REVOKED）状态的公告才能被发布。

### 接口原型

- 请求方法: POST
- 请求地址: `/api/announcements/{id}/publish`
- 传输协议: HTTP

### 请求头

| 参数名 | 参数值 | 是否必须 | 描述 |
| ------ | ------ | -------- | ---- |
| Accept | application/json | 是 | 指定响应的内容类型 |

### 请求参数

| 参数名 | 类型 | 位置 | 是否必须 | 描述 |
| ------ | ---- | ---- | -------- | ---- |
| id | Long | path | 是 | 公告ID |

### 响应头

| 参数名 | 参数值 | 描述 |
| ------ | ------ | ---- |
| Content-Type | application/json | 响应的内容类型 |

### 响应状态码

| 状态码 | 描述 |
| ------ | ---- |
| 200 | 发布成功 |
| 400 | 状态不允许发布 |
| 404 | 公告不存在 |

### 响应参数

返回发布后的公告对象，包含以下字段：

| 参数名 | 类型 | 描述 |
| ------ | ---- | ---- |
| id | Long | 公告ID |
| title | String | 公告标题 |
| content | String | 公告内容 |
| status | String | 公告状态（PUBLISHED） |
| createdBy | String | 创建人 |
| createdAt | String | 创建时间 |
| updatedBy | String | 更新人 |
| updatedAt | String | 更新时间 |
| publishDate | String | 发布时间 |
| expiryDate | String | 过期时间 |

### 请求示例

```bash
curl -X POST http://localhost:8080/api/announcements/4/publish
```

### 响应示例

```json
{
  "id": 4,
  "title": "Updated Announcement",
  "content": "This is an updated announcement",
  "status": "PUBLISHED",
  "createdBy": "admin",
  "createdAt": "2025-03-20T02:57:02.514993",
  "updatedBy": "admin",
  "updatedAt": "2025-03-20T02:57:12.525751255",
  "publishDate": "2025-03-20T02:57:12.525766654",
  "expiryDate": null
}
```

---

## 7. 撤销公告

### 接口功能描述

将指定的公告状态更改为已撤销（REVOKED）。只有处于已发布（PUBLISHED）状态的公告才能被撤销。

### 接口原型

- 请求方法: POST
- 请求地址: `/api/announcements/{id}/revoke`
- 传输协议: HTTP

### 请求头

| 参数名 | 参数值 | 是否必须 | 描述 |
| ------ | ------ | -------- | ---- |
| Accept | application/json | 是 | 指定响应的内容类型 |

### 请求参数

| 参数名 | 类型 | 位置 | 是否必须 | 描述 |
| ------ | ---- | ---- | -------- | ---- |
| id | Long | path | 是 | 公告ID |

### 响应头

| 参数名 | 参数值 | 描述 |
| ------ | ------ | ---- |
| Content-Type | application/json | 响应的内容类型 |

### 响应状态码

| 状态码 | 描述 |
| ------ | ---- |
| 200 | 撤销成功 |
| 400 | 状态不允许撤销 |
| 404 | 公告不存在 |

### 响应参数

返回撤销后的公告对象，包含以下字段：

| 参数名 | 类型 | 描述 |
| ------ | ---- | ---- |
| id | Long | 公告ID |
| title | String | 公告标题 |
| content | String | 公告内容 |
| status | String | 公告状态（REVOKED） |
| createdBy | String | 创建人 |
| createdAt | String | 创建时间 |
| updatedBy | String | 更新人 |
| updatedAt | String | 更新时间 |
| publishDate | String | 发布时间 |
| expiryDate | String | 过期时间 |

### 请求示例

```bash
curl -X POST http://localhost:8080/api/announcements/4/revoke
```

### 响应示例

```json
{
  "id": 4,
  "title": "Updated Announcement",
  "content": "This is an updated announcement",
  "status": "REVOKED",
  "createdBy": "admin",
  "createdAt": "2025-03-20T02:57:02.514993",
  "updatedBy": "admin",
  "updatedAt": "2025-03-20T02:57:12.537953147",
  "publishDate": "2025-03-20T02:57:12.525767",
  "expiryDate": null
}
```

---

## 8. 调度公告

### 接口功能描述

为指定的公告设置发布时间和过期时间。只有处于草稿（DRAFT）状态的公告才能被调度。

### 接口原型

- 请求方法: POST
- 请求地址: `/api/announcements/{id}/schedule`
- 传输协议: HTTP

### 请求头

| 参数名 | 参数值 | 是否必须 | 描述 |
| ------ | ------ | -------- | ---- |
| Content-Type | application/json | 是 | 指定请求的内容类型 |
| Accept | application/json | 是 | 指定响应的内容类型 |

### 请求参数

| 参数名 | 类型 | 位置 | 是否必须 | 描述 |
| ------ | ---- | ---- | -------- | ---- |
| id | Long | path | 是 | 公告ID |
| publishDate | String | body | 是 | 发布时间 |
| expiryDate | String | body | 是 | 过期时间 |

### 响应头

| 参数名 | 参数值 | 描述 |
| ------ | ------ | ---- |
| Content-Type | application/json | 响应的内容类型 |

### 响应状态码

| 状态码 | 描述 |
| ------ | ---- |
| 200 | 调度成功 |
| 400 | 状态不允许调度或参数错误 |
| 404 | 公告不存在 |

### 响应参数

返回调度后的公告对象，包含以下字段：

| 参数名 | 类型 | 描述 |
| ------ | ---- | ---- |
| id | Long | 公告ID |
| title | String | 公告标题 |
| content | String | 公告内容 |
| status | String | 公告状态 |
| createdBy | String | 创建人 |
| createdAt | String | 创建时间 |
| updatedBy | String | 更新人 |
| updatedAt | String | 更新时间 |
| publishDate | String | 发布时间 |
| expiryDate | String | 过期时间 |

### 请求示例

```bash
curl -X POST http://localhost:8080/api/announcements/2/schedule \
  -H "Content-Type: application/json" \
  -d '{"publishDate":"2025-04-01T12:00:00","expiryDate":"2025-04-30T12:00:00"}'
```

### 响应示例

```json
{
  "id": 2,
  "title": "Upcoming Features",
  "content": "Stay tuned for exciting new features coming soon! This is a draft announcement.",
  "status": "DRAFT",
  "createdBy": "admin",
  "createdAt": "2025-03-18T02:56:32.748978",
  "updatedBy": "admin",
  "updatedAt": "2025-03-20T02:57:12.552597412",
  "publishDate": "2025-04-01T12:00:00",
  "expiryDate": "2025-04-30T12:00:00"
}
```

---

## 9. 搜索公告

### 接口功能描述

根据标题和状态搜索公告。

### 接口原型

- 请求方法: GET
- 请求地址: `/api/announcements/search`
- 传输协议: HTTP

### 请求头

| 参数名 | 参数值 | 是否必须 | 描述 |
| ------ | ------ | -------- | ---- |
| Accept | application/json | 是 | 指定响应的内容类型 |

### 请求参数

| 参数名 | 类型 | 位置 | 是否必须 | 描述 |
| ------ | ---- | ---- | -------- | ---- |
| title | String | query | 否 | 公告标题（模糊匹配） |
| status | String | query | 否 | 公告状态（DRAFT, PUBLISHED, REVOKED, EXPIRED） |

### 响应头

| 参数名 | 参数值 | 描述 |
| ------ | ------ | ---- |
| Content-Type | application/json | 响应的内容类型 |

### 响应状态码

| 状态码 | 描述 |
| ------ | ---- |
| 200 | 成功 |

### 响应参数

返回符合条件的公告对象数组，每个公告对象包含以下字段：

| 参数名 | 类型 | 描述 |
| ------ | ---- | ---- |
| id | Long | 公告ID |
| title | String | 公告标题 |
| content | String | 公告内容 |
| status | String | 公告状态 |
| createdBy | String | 创建人 |
| createdAt | String | 创建时间 |
| updatedBy | String | 更新人 |
| updatedAt | String | 更新时间 |
| publishDate | String | 发布时间 |
| expiryDate | String | 过期时间 |

### 请求示例

```bash
curl -X GET "http://localhost:8080/api/announcements/search?title=Welcome&status=PUBLISHED"
```

### 响应示例

```json
[
  {
    "id": 1,
    "title": "Welcome to Benefly",
    "content": "Welcome to the new announcement system. This is a published announcement.",
    "status": "PUBLISHED",
    "createdBy": "admin",
    "createdAt": "2025-03-15T02:56:32.711634",
    "updatedBy": "admin",
    "updatedAt": "2025-03-15T02:56:32.711663",
    "publishDate": "2025-03-15T02:56:32.711671",
    "expiryDate": "2025-04-14T02:56:32.711677"
  }
]
```
