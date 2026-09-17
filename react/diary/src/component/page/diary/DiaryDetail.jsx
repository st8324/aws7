import { useEffect, useState } from "react"
import { useAuth } from "../../../provider/AuthContext"
import { useParams } from "react-router-dom";
import { Container, Card, Badge, Alert } from "react-bootstrap";

export function DiaryDetail({ isPublic }) {
	const [diary, setDiary] = useState(null);
	const { authFetch } = useAuth();
	const { id } = useParams();

	const loadMyDiary = async (id) => {
		try {
			const response = await authFetch("/api/diaries/" + id);
			const result = await response.json();
			setDiary(result);
		} catch {

		}
	}
	const loadPublicDiary = async (id) => {
		try {
			const response = await authFetch("/api/diaries/public/" + id);
			const result = await response.json();
			setDiary(result);
		} catch {

		}
	}


	useEffect(() => {
		if (isPublic) {
			loadPublicDiary(id);
		} else {
			loadMyDiary(id);
		}
	}, [id, isPublic])

	return (
		<Container className="py-4" style={{ maxWidth: "700px" }}>
			<h1 className="mb-4 fs-3 fw-bold">일기 상세</h1>

			{
				diary && diary.diaryId ?
					<Card className="shadow-sm">
						<Card.Body className="p-4">
							<div className="d-flex justify-content-between align-items-start mb-3">
								<Card.Title as="h2" className="fs-4 fw-semibold mb-1">
									{diary.title}
								</Card.Title>
								<span className="text-muted small text-nowrap ms-3">
									{diary.diaryDate}
								</span>
							</div>

							{diary.emotions && diary.emotions.length > 0 && (
								<div className="d-flex flex-wrap gap-2 mb-3">
									{diary.emotions.map(emo => (
										<Badge key={emo.id} bg="light" text="dark" className="border rounded-pill px-2 py-1">
											{emo.emoji} {emo.name}
										</Badge>
									))}
								</div>
							)}

							<hr />

							<Card.Text className="mt-3" style={{ whiteSpace: "pre-wrap", lineHeight: 1.7 }}>
								{diary.content}
							</Card.Text>
						</Card.Body>
					</Card>
					:
					<Alert variant="light" className="text-center text-muted border">
						올바른 접근이 아닙니다.
					</Alert>
			}
		</Container>
	)
}
