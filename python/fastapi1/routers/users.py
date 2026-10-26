from fastapi import APIRouter, HTTPException, status

router = APIRouter(prefix="/users", tags=["users"])

@router.get("/{user_id}")
def get_user(user_id: int) -> dict:
    return {"user_id" : user_id}
