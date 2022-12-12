export interface PostRequest {
  name: string,
  url?: string,
  description?: string,
  userId: bigint,
  subredditId: bigint
}
