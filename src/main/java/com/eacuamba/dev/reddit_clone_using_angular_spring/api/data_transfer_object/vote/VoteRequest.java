package com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.vote;

import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.vote.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteRequest {
    private VoteType voteType;
    private Long postId;
}
