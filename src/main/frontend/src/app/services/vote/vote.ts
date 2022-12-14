export interface VoteRequest {
  voteType: string;
  postId: bigint
}

export enum VoteType {
  UP,
  DOWN
}
