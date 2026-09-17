import { useEffect, useState } from "react";
import { Container, Tabs, Tab, Card, Badge, Spinner, Alert, Stack } from "react-bootstrap";
import { Link } from "react-router-dom";
import { useAuth } from "../../../provider/AuthContext";

export function DiaryList() {

	const [activeTab, setActiveTab] = useState("mine");

	return (
		<Container className="py-4" style={{ maxWidth: "700px" }}>
			<h1 className="mb-4 fs-3 fw-bold">일기 목록</h1>

			<Tabs
				activeKey={activeTab}
				onSelect={(key) => setActiveTab(key)}
				className="mb-3"
				justify
			>
				<Tab eventKey="mine" title="내 일기" />
				<Tab eventKey="public" title="공개 일기" />
			</Tabs>
			{
				activeTab === 'mine' ? <MyDiaries /> : <PublicDiaries />
			}
		</Container>
	);
}

function MyDiaries() {
	const [diaries, setDiaries] = useState([]);
	const [loading, setLoading] = useState(true);
	const { authFetch } = useAuth();

	const loadMyDiaries = async () => {
		// 서버에서 내 일기 목록 가져오기
		setLoading(true);
		try {
			const response = await authFetch("/api/diaries");
			const result = await response.json();
			setDiaries(result);
		} catch (e) {
			console.error(e);
		} finally {
			setLoading(false);
		}
	};

	useEffect(() => {
		loadMyDiaries();
	}, []);

	if (loading) {
		return (
			<div className="text-center py-5">
				<Spinner animation="border" variant="primary" />
			</div>
		);
	}

	if (diaries.length === 0) {
		return (
			<Alert variant="light" className="text-center text-muted border">
				아직 작성한 일기가 없습니다.
			</Alert>
		);
	}

	return (
		<Stack gap={3}>
			{diaries.map(diary => (
				<DiaryCard key={diary.diaryId} diary={diary} showAuthor={false} />
			))}
		</Stack>
	);
}

function PublicDiaries() {

	const [diaries, setDiaries] = useState([]);
	const [loading, setLoading] = useState(true);
	const { authFetch } = useAuth();

	const loadPublicDiaries = async () => {
		// 서버에서 공개 일기 목록 가져오기
		setLoading(true);
		try {
			const response = await authFetch("/api/diaries/public");
			const result = await response.json();
			setDiaries(result);
		} catch (e) {
			console.error(e);
		} finally {
			setLoading(false);
		}
	};

	useEffect(() => {
		loadPublicDiaries();
	}, []);

	if (loading) {
		return (
			<div className="text-center py-5">
				<Spinner animation="border" variant="primary" />
			</div>
		);
	}

	if (diaries.length === 0) {
		return (
			<Alert variant="light" className="text-center text-muted border">
				공개된 일기가 없습니다.
			</Alert>
		);
	}

	return (
		<Stack gap={3}>
			{diaries.map(diary => (
				<DiaryCard key={diary.diaryId} diary={diary} showAuthor={true} />
			))}
		</Stack>
	);
}

function DiaryCard({ diary, showAuthor }) {
	return (
		<Card
			as={Link}
			to={"/diary/detail/" + diary.diaryId}
			className="shadow-sm text-decoration-none text-body"
		>
			<Card.Body className="d-flex justify-content-between align-items-start">
				<div className="me-3">
					<div className="text-muted small mb-1">
						{diary.diaryDate}
						{showAuthor && diary.nickname && ` · ${diary.nickname}`}
					</div>
					<Card.Title as="div" className="fw-semibold mb-0 fs-5">
						{diary.title}
					</Card.Title>
				</div>

				{diary.emotions && diary.emotions.length > 0 && (
					<div className="d-flex flex-wrap gap-1 justify-content-end" style={{ maxWidth: "40%" }}>
						{diary.emotions.map(emo => (
							<Badge key={emo.id} bg="light" text="dark" className="border rounded-pill">
								{emo.emoji} {emo.name}
							</Badge>
						))}
					</div>
				)}
			</Card.Body>
		</Card>
	);
}
