import { useCallback, useEffect, useState } from 'react'
import { getHealth } from './api'
import StatusBar from './components/StatusBar'
import PhotoCard from './components/PhotoCard'
import PriceCard from './components/PriceCard'
import ListingCard from './components/ListingCard'

// 세 카드가 함께 쓰는 차 정보. JSON 이름(snake_case) 그대로 쓴다.
const INITIAL_CAR = {
  car_type: 'SUV',
  year_age: 3,
  mileage_km: 40000,
  engine_cc: 2500,
  accident: 0,
}

export default function App() {
  const [health, setHealth] = useState(null) // null = 아직 확인 전
  const [checking, setChecking] = useState(false)
  const [car, setCar] = useState(INITIAL_CAR)
  const [price, setPrice] = useState(null) // 예측한 가격 (만 원)

  const checkHealth = useCallback(async () => {
    setChecking(true)
    try {
      setHealth(await getHealth())
    } catch (e) {
      setHealth({ ai_server: false, web_server: false, error: e.message, features: {} })
    } finally {
      setChecking(false)
    }
  }, [])

  useEffect(() => {
    checkHealth()
  }, [checkHealth])

  const features = health?.features ?? {}

  // 차 정보가 바뀌면 예전 가격은 더 이상 맞지 않는다
  const updateCar = (patch) => {
    setCar((prev) => ({ ...prev, ...patch }))
    setPrice(null)
  }

  return (
    <div className="page">
      <header className="header">
        <h1>🚗 중고차 AI 서비스</h1>
        <p>1 시세 예측 · 2 사진으로 차종 알아보기 · 3 판매글 작성</p>
      </header>

      <StatusBar health={health} checking={checking} onRefresh={checkHealth} />

      <main className="cards">
        <PriceCard
          ready={features.price}
          car={car}
          onChange={updateCar}
          price={price}
          onPrice={setPrice}
        />
        <PhotoCard
          ready={features.car_type}
          onCarType={(carType) => updateCar({ car_type: carType })}
        />
        <ListingCard
          ready={features.listing}
          car={car}
          price={price}
          onPrice={setPrice}
        />
      </main>
    </div>
  )
}
