import os, time
from google import genai
from google.genai import errors, types
from dotenv import dotenv_values, find_dotenv

KEY_NAME = "GEMINI_API_KEY"

# 1) .env 파일에서 먼저 찾기 (현재 폴더부터 상위 폴더로 올라가며 탐색)
env_path = find_dotenv(usecwd=True)
api_key = dotenv_values(env_path).get(KEY_NAME) if env_path else None
source = ".env 파일" if api_key else None

# 2) .env에 없으면 시스템 환경 변수에서 찾기
if not api_key:
    api_key = os.environ.get(KEY_NAME)
    source = "시스템 환경 변수" if api_key else None

if not api_key:
    raise SystemExit(
        f"{KEY_NAME}를 찾을 수 없습니다.\n"
        f"- .env 파일에 {KEY_NAME}=발급받은키 를 추가하거나\n"
        f"- 시스템 환경 변수로 등록한 뒤 터미널(주피터)을 다시 시작하세요"
    )

client = genai.Client(api_key=api_key)
MODELS = ["gemini-3.1-flash-lite", "gemini-2.5-flash", "gemini-2.5-flash-lite"]

def ask(contents, **config):
    # 무료 사용중이라 요청이 밀릴수 있어서 다양한 모델에 여러번 시도
    for model in MODELS:
        for wait in [0, 2, 4]:
            time.sleep(wait)
            try:
                r = client.models.generate_content(
                    model=model, contents=contents,
                    config=types.GenerateContentConfig(**config) if config else None)
                print(f"  ({model} 응답)")
                return r
            except errors.APIError as e:
                print(f"  [{model}] {e.code} 에러: {e.message}")
                if e.code == 503:
                    continue      # 서버가 바쁨: 잠시 후 같은 모델로 다시 시도
                if e.code in (429, 404):
                    break         # 한도 초과 또는 없는 모델: 다음 모델로
                raise             # 400 등: 요청이나 코드 문제이므로 바로 멈춤
    raise SystemExit("모든 모델이 응답하지 않습니다. 잠시 후 다시 실행하세요")