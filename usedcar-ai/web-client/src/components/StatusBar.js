// 서버 상태 표시줄. /api/health 결과를 보여 준다.

const FEATURES = [
  { key: 'price', label: '1 시세 예측' },
  { key: 'car_type', label: '2 사진 분류' },
  { key: 'listing', label: '3 판매글' },
]

export default function StatusBar({ health, checking, onRefresh }) {
  let server
  if (!health) {
    server = <span className="dot gray">확인 중…</span>
  } else if (health.web_server === false) {
    server = <span className="dot red">{health.error}</span>
  } else if (!health.ai_server) {
    server = <span className="dot red">AI 서버(FastAPI 8000)에 연결할 수 없습니다</span>
  } else {
    server = <span className="dot green">AI 서버 연결됨</span>
  }

  return (
    <div className="status">
      <div className="status-server">{server}</div>
      <div className="status-features">
        {FEATURES.map((f) => (
          <span key={f.key} className={`chip ${health?.features?.[f.key] ? 'on' : 'off'}`}>
            {f.label} {health?.features?.[f.key] ? '사용 가능' : '준비 중'}
          </span>
        ))}
      </div>
      <button className="btn small" onClick={onRefresh} disabled={checking}>
        {checking ? '확인 중…' : '다시 확인'}
      </button>
    </div>
  )
}
