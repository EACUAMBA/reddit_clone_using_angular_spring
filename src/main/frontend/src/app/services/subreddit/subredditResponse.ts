export interface SubredditResponse {
  id: bigint,
  name: string,
  description: string,
  numberOfPosts: bigint,
  userUsername: string,
  userId: bigint
}

export interface SubredditRequest {
  name: string,
  descrption: string
}
