import { useState } from 'react'
import { writeListing } from '../api'
import FeatureCard from './FeatureCard'

// 3단계 (AI): 차 정보와 가격으로 판매글 초안을 만든다.

const TONES = ['친근하게', '전문적으로', '짧게']

export default function ListingCard({ ready, car, price, onPrice }) {
  const [memo, setMemo] = useState('')
  const [tone, setTone] = useState(TONES[0])
  const [listing, setListing] = useState(null)
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const [copied, setCopied] = useState(false)

  const onWrite = async () => {
    setLoading(true)
    setError('')
    setCopied(false)
    try {
      setListing(await writeListing({ ...car, price, memo, tone }))
    } catch (e) {
      setError(e.message)
    } finally {
      setLoading(false)
    }
  }

  const onCopy = async () => {
    try {
      await navigator.clipboard.writeText(`${listing.title}\n\n${listing.body}`)
      setCopied(true)
    } catch {
      setError('복사하지 못했습니다. 직접 선택해서 복사해 주세요')
    }
  }

  return (
    <FeatureCard
      step="3"
      title="판매글 작성"
      description="AI · LLM API + 프롬프트"
      ready={ready}
    >
      <div className="form">
        <label>
          판매 가격
          <span className="with-unit">
            <input
              type="number"
              min={1}
              value={price ?? ''}
              placeholder="시세 예측을 먼저 하거나 직접 입력"
              onChange={(e) => onPrice(e.target.value === '' ? null : Number(e.target.value))}
            />
            <em>만 원</em>
          </span>
        </label>

        <label>
          강조하고 싶은 점 (선택)
          <textarea
            rows={3}
            maxLength={200}
            value={memo}
            placeholder="예: 금연차, 정기 점검 받음"
            onChange={(e) => setMemo(e.target.value)}
          />
          <small>{memo.length} / 200자</small>
        </label>

        <label>
          말투
          <select value={tone} onChange={(e) => setTone(e.target.value)}>
            {TONES.map((t) => (
              <option key={t}>{t}</option>
            ))}
          </select>
        </label>

        <button className="btn" onClick={onWrite} disabled={!ready || !price || loading}>
          {loading ? '작성 중… (몇 초 걸려요)' : '판매글 만들기'}
        </button>
      </div>

      {error && <p className="error">{error}</p>}

      {listing && (
        <div className="result listing">
          <h3>{listing.title}</h3>
          <p className="listing-body">{listing.body}</p>
          <button className="btn small" onClick={onCopy}>
            {copied ? '복사했어요' : '복사하기'}
          </button>
          <p className="hint">AI가 쓴 초안이에요. 올리기 전에 사실과 다른 내용이 없는지 꼭 확인하세요.</p>
        </div>
      )}
    </FeatureCard>
  )
}
