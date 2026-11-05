// Spring(/api)을 부르는 함수 모음.
// React는 FastAPI를 직접 부르지 않는다. 항상 Spring을 거친다.

async function request(path, options = {}) {
  let res
  try {
    res = await fetch(path, options)
  } catch {
    throw new Error('웹 서버(Spring)에 연결할 수 없습니다. 8080이 켜져 있는지 확인하세요')
  }

  // Spring은 에러를 { "message": "..." } 로 보낸다
  const data = await res.json().catch(() => null)
  if (!res.ok) {
    // 개발 서버(proxy)가 Spring에 닿지 못하면 JSON이 아닌 500 에러가 온다
    const message =
      data?.message ??
      (res.status >= 500
        ? '웹 서버(Spring)에 연결할 수 없습니다. 8080이 켜져 있는지 확인하세요'
        : `요청에 실패했습니다 (${res.status})`)
    throw new Error(message)
  }
  return data
}

function postJson(path, body) {
  return request(path, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body),
  })
}

// GET /api/health → { ai_server, features: { price, car_type, listing } }
export function getHealth() {
  return request('/api/health')
}

// POST /api/price → { price }
export function predictPrice(car) {
  return postJson('/api/price', car)
}

// POST /api/car-type (multipart, 필드 이름 file)
// → { car_type, confidence, scores, needs_confirm, message }
export function classifyCarType(file) {
  const form = new FormData()
  form.append('file', file)
  // Content-Type은 직접 쓰지 않는다. 브라우저가 boundary까지 넣어서 채워 준다.
  return request('/api/car-type', { method: 'POST', body: form })
}

// POST /api/listing → { title, body }
export function writeListing(payload) {
  return postJson('/api/listing', payload)
}
