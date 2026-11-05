# 중고차 AI 서비스

React와 Spring은 완성본이다. 우리가 만드는 것은 **FastAPI 서버(ai-server) 하나**다.
ai-server는 기본 설정이 끝난 0단계 상태로, 모든 API가 고정값으로 응답한다.
API 주소와 JSON 모양은 노션의 「중고차 AI 서비스: FastAPI 명세」를 따른다.

```
[React :3000]   →  [Spring :8080]  →  [FastAPI :8000]   ← 우리가 만드는 부분
 web-client        web-server         ai-server
```

| 폴더 | 내용 | 상태 |
| --- | --- | --- |
| `web-client/` | React 화면 (Create React App) | 완성 |
| `web-server/` | Spring Boot. 입력 검증, 결과 후처리, 에러 처리 | 완성 |
| `ai-server/` | FastAPI. 모델 불러오기, 예측 | **0단계 (고정값 응답). TODO를 채운다** |

## 준비물

- Java 17 이상
- Node.js 18 이상
- Python 3.11 이상

## 실행 순서

터미널을 3개 연다. 순서는 상관없지만, 보통 뒤에서부터 켠다.

**1. FastAPI (8000)**

```bash
cd ai-server
.venv\Scripts\activate     # 처음 설정은 ai-server/README.md 참고
uvicorn main:app --reload --port 8000
```

- 브라우저에서 http://localhost:8000/docs 로 API를 바로 시험할 수 있다

**2. Spring (8080)**

```bash
cd web-server
./gradlew bootRun        # Windows: gradlew.bat bootRun
```

- IntelliJ에서 `WebServerApplication` 을 실행해도 된다

**3. React (3000)**

```bash
cd web-client
npm install              # 처음 한 번만
npm start
```

- 브라우저가 http://localhost:3000 으로 열린다
- `/api` 요청은 `package.json` 의 `"proxy"` 설정으로 Spring(8080)에 넘어간다

## 화면에서 확인하기

- 위쪽 상태 표시줄이 `/health` 결과를 보여 준다
  - `AI 서버(FastAPI 8000)에 연결할 수 없습니다` → FastAPI가 꺼져 있다
  - `웹 서버(Spring)에 연결할 수 없습니다` → Spring이 꺼져 있다
- FastAPI `/health` 의 `features` 에서 `true` 인 기능만 카드가 열린다. 나머지는 "준비 중"
- FastAPI를 고친 뒤에는 화면의 **다시 확인** 버튼을 누른다

## 에러 메시지 읽는 법

화면에 나오는 메시지만 보고도 어디가 문제인지 알 수 있게 만들어 두었다.

| 화면 메시지 | 원인 | 볼 곳 |
| --- | --- | --- |
| AI 서버에 연결할 수 없습니다 | FastAPI가 꺼져 있음 | FastAPI 터미널 |
| AI 서버에 /price/predict 주소가 아직 없습니다 | 주소를 안 만들었거나 오타 | FastAPI 라우터 |
| AI 서버 코드에서 오류가 났습니다 (500) | FastAPI 코드가 실행 중에 터짐 | FastAPI 터미널의 에러 |
| AI 서버 응답이 명세와 다릅니다: ... | JSON 이름이나 모양이 명세와 다름 | 응답 JSON (`/docs`에서 확인) |
| 입력을 확인해 주세요 (AI 서버가 요청 형식을 받아들이지 않았습니다) | FastAPI의 Pydantic 모델이 명세와 다름 | 요청 모델 필드 이름, 타입 |
| AI 서버 응답이 너무 늦습니다 | 시간 초과 (시세 3초, 사진 15초, 판매글 30초) | 모델을 요청마다 불러오고 있지 않은지 |

## Spring이 하는 일 (DB가 없어도)

- **입력 검증**: 학습 데이터 범위 밖 값(연식 20년 등)은 FastAPI까지 보내지 않는다 (`dto/PriceRequest.java`)
- **결과 후처리**: 음수 가격은 0으로, 확신이 50% 미만인 분류는 "직접 확인해 주세요" (`service/AiService.java`)
- **장애 처리**: FastAPI가 꺼짐, 느림, 에러를 각각 다른 메시지로 바꾼다 (`client/AiClient.java`)
- **비밀 값 보호**: LLM API 키는 FastAPI의 `.env` 에만 있다. 브라우저(React)에는 절대 가지 않는다

## 요청 한 번 따라가기 (시세 예측)

1. `web-client/src/components/PriceCard.js` 버튼 클릭 → `predictPrice(car)`
2. `web-client/src/api.js` → `POST /api/price` (개발 서버 proxy가 8080으로 넘김)
3. `web-server/.../controller/AiController.java` `price()` → `@Valid` 검증
4. `web-server/.../service/AiService.java` `predictPrice()`
5. `web-server/.../client/AiClient.java` → `POST http://localhost:8000/price/predict`
6. **ai-server** 가 `{ "price": 1850 }` 응답
7. 거꾸로 돌아와 화면에 `1,850만 원`

## 설정 바꾸기

`web-server/src/main/resources/application.yml`

```yaml
ai-server:
  base-url: http://localhost:8000   # FastAPI 주소
  timeout:
    price: 3s
    car-type: 15s
    listing: 30s
```

## 자주 나는 문제

**`npm start` 할 때 `options.allowedHosts[0] should be a non-empty string`**

CRA의 proxy 설정과 네트워크 환경이 겹칠 때 나는 오류다. `web-client/.env` 파일을 만들고 아래 한 줄을 넣은 뒤 다시 실행한다.

```
DANGEROUSLY_DISABLE_HOST_CHECK=true
```

개발용 설정이라 수업 PC에서만 쓴다.
