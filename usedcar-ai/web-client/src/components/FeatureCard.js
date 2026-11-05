// 기능 카드 틀. ready 가 false면 "준비 중"으로 덮는다.

export default function FeatureCard({ step, title, description, ready, children }) {
  return (
    <section className={`card ${ready ? '' : 'disabled'}`}>
      <div className="card-head">
        <span className="step">{step}</span>
        <div>
          <h2>{title}</h2>
          <p className="desc">{description}</p>
        </div>
      </div>
      <div className="card-body">
        {children}
        {!ready && (
          <div className="overlay">
            <span>준비 중</span>
            <small>FastAPI에서 이 기능을 만들면 열립니다</small>
          </div>
        )}
      </div>
    </section>
  )
}
