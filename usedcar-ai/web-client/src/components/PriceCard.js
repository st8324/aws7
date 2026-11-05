import { useState } from 'react'
import { predictPrice } from '../api'
import FeatureCard from './FeatureCard'

// 1단계 (머신러닝): 차 정보를 넣으면 시세를 예측한다.
// 입력 범위는 학습 데이터(used_cars.csv)의 범위와 같다.

const CAR_TYPES = ['경차', '세단', 'SUV']

const NUMBER_FIELDS = [
  { key: 'year_age', label: '연식 (출고 후 몇 년)', min: 0, max: 15, step: 1, unit: '년' },
  { key: 'mileage_km', label: '주행거리', min: 5000, max: 200000, step: 1000, unit: 'km' },
  { key: 'engine_cc', label: '배기량', min: 1000, max: 3500, step: 100, unit: 'cc' },
]

export default function PriceCard({ ready, car, onChange, price, onPrice }) {
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const onSubmit = async (e) => {
    e.preventDefault()
    setLoading(true)
    setError('')
    try {
      const res = await predictPrice(car)
      onPrice(res.price)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  // 숫자 칸을 비우면 null 로 보낸다 → Spring이 "입력해 주세요"로 막는다
  const setNumber = (key) => (e) =>
    onChange({ [key]: e.target.value === '' ? null : Number(e.target.value) })

  return (
    <FeatureCard
      step="1"
      title="시세 예측"
      description="머신러닝 · used_cars.csv로 학습한 모델"
      ready={ready}
    >
      <form className="form" onSubmit={onSubmit}>
        <label>
          차종
          <select value={car.car_type} onChange={(e) => onChange({ car_type: e.target.value })}>
            {CAR_TYPES.map((t) => (
              <option key={t}>{t}</option>
            ))}
          </select>
        </label>

        {NUMBER_FIELDS.map((f) => (
          <label key={f.key}>
            {f.label}
            <span className="with-unit">
              <input
                type="number"
                min={f.min}
                max={f.max}
                step={f.step}
                value={car[f.key] ?? ''}
                onChange={setNumber(f.key)}
              />
              <em>{f.unit}</em>
            </span>
            <small>
              {f.min.toLocaleString()} ~ {f.max.toLocaleString()}
              {f.unit}
            </small>
          </label>
        ))}

        <label>
          사고 이력
          <select
            value={car.accident}
            onChange={(e) => onChange({ accident: Number(e.target.value) })}
          >
            <option value={0}>없음</option>
            <option value={1}>있음</option>
          </select>
        </label>

        <button className="btn" type="submit" disabled={!ready || loading}>
          {loading ? '예측 중…' : '시세 예측하기'}
        </button>
      </form>

      {error && <p className="error">{error}</p>}

      {price !== null && (
        <div className="result">
          <p className="result-label">예상 시세</p>
          <p className="result-price">{price.toLocaleString()}만 원</p>
        </div>
      )}
    </FeatureCard>
  )
}
