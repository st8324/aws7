import { useEffect, useState } from 'react'
import { classifyCarType } from '../api'
import FeatureCard from './FeatureCard'

// 2단계 (딥러닝): 사진을 올리면 차종을 알려 준다.
// 결과는 시세 예측 카드의 차종 칸에 자동으로 들어간다.

const MAX_SIZE = 5 * 1024 * 1024

export default function PhotoCard({ ready, onCarType }) {
  const [file, setFile] = useState(null)
  const [preview, setPreview] = useState(null)
  const [result, setResult] = useState(null)
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  // 미리보기 주소는 다 쓰면 정리한다
  useEffect(() => {
    if (!file) return
    const url = URL.createObjectURL(file)
    setPreview(url)
    return () => URL.revokeObjectURL(url)
  }, [file])

  const onSelect = (e) => {
    const f = e.target.files?.[0]
    setResult(null)
    setError('')
    if (!f) return
    if (!['image/jpeg', 'image/png'].includes(f.type)) {
      setError('jpg 또는 png 사진만 올릴 수 있습니다')
      return
    }
    if (f.size > MAX_SIZE) {
      setError('사진은 5MB 이하만 올릴 수 있습니다')
      return
    }
    setFile(f)
  }

  const onClassify = async () => {
    setLoading(true)
    setError('')
    try {
      const res = await classifyCarType(file)
      setResult(res)
      onCarType(res.car_type)
    } catch (e) {
      setError(e.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <FeatureCard
      step="2"
      title="사진으로 차종 알아보기"
      description="딥러닝 · 사전학습 이미지 모델"
      ready={ready}
    >
      <label className="file">
        <input type="file" accept="image/jpeg,image/png" onChange={onSelect} />
        <span>{file ? file.name : '사진 고르기 (jpg, png / 5MB 이하)'}</span>
      </label>

      {preview && <img className="preview" src={preview} alt="올린 사진" />}

      <button className="btn" onClick={onClassify} disabled={!ready || !file || loading}>
        {loading ? '분류 중… (조금 걸려요)' : '차종 분류하기'}
      </button>

      {error && <p className="error">{error}</p>}

      {result && (
        <div className={`result ${result.needs_confirm ? 'warn' : ''}`}>
          <p className="result-main">{result.message}</p>
          <ul className="scores">
            {Object.entries(result.scores).map(([label, score]) => (
              <li key={label}>
                <span className="score-label">{label}</span>
                <span className="bar">
                  <span style={{ width: `${Math.round(score * 100)}%` }} />
                </span>
                <span className="score-value">{Math.round(score * 100)}%</span>
              </li>
            ))}
          </ul>
          <p className="hint">차종 칸에 자동으로 넣었어요. 틀렸으면 아래에서 직접 바꿔 주세요.</p>
        </div>
      )}
    </FeatureCard>
  )
}
