# Starsnap File Upload Lambda

## 📦 개요

`Starsnap File Upload Lambda`는 S3 이벤트 기반으로 동작하는 파일 업로드 처리 Lambda 함수입니다.  
Spring Cloud Function 기반으로 AWS Lambda에서 실행되며,  
**starsnap-input** 버킷에 업로드된 파일을 읽어 **starsnap-output** 버킷으로 저장합니다.

---

## 🛠️ 기술 스택

- **AWS Lambda** (`java17`)
- **AWS SAM (Serverless Application Model)**
- **Spring Cloud Function**
- **Kotlin**
- **S3 파일 처리**

---

## 🚀 실행 방법

### 로컬 실행 (테스트)

1. **빌드**

```bash
sam build
sam deploy
```

2. **람다 적용**
