export interface PostResponsePayload{
  id: bigint,
  name: string,
  url: string,
  description: string,
  subrreditId: bigint,
  subredditName: string,
  userUsername: string,
  userId: bigint,

  voteCount: bigint,
  commentsCount: bigint,
  duration: string,
  downVote: boolean,
  upVote: boolean
}
