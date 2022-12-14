export interface CommentResponse {
  id: bigint,
  text: string,
  postId: bigint,
  userId: bigint,
  userUsername: string,
  timeAgo: string
}

export interface CommentResquest {
  text: string,
  postId: bigint
}
