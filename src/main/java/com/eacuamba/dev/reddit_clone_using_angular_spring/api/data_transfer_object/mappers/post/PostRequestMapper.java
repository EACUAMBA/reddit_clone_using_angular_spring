package com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.mappers.post;

import com.eacuamba.dev.reddit_clone_using_angular_spring.api.data_transfer_object.request.post.PostRequest;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Post;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.Subreddit;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.model.User;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.SubredditService;
import com.eacuamba.dev.reddit_clone_using_angular_spring.domain.service.user_service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostRequestMapper {
    private final ModelMapper modelMapper;
    private final SubredditService subredditService;
    private final UserService userService;

    public Post mapToPost(PostRequest postRequest){
        Post post = this.modelMapper.map(postRequest, Post.class);

        User user = this.userService.findById(postRequest.getUserId());
        post.setUser(user);

        Subreddit subreddit = subredditService.findById(postRequest.getSubredditId());
        post.setSubreddit(subreddit);

        return post;
    }

    public PostRequest mapToPostRequest(Post post){
        return new PostRequest();
    }

//    default User getUser(Long userId, @Context UserRepository userRepository){
//        Optional<User> optionalUser = userRepository.findById(userId);
//        return optionalUser.orElseThrow(() -> new UsernameNotFoundException(String.format("User with id = %s was not found.", userId)));
//    }
//    default Subreddit getSubreddit(Long subredditId, @Context SubredditRepository subredditRepository){
//        Optional<Subreddit> optionalSubreddit = subredditRepository.findById(subredditId);
//        return optionalSubreddit.orElseThrow(() -> new RedditCloneException(String.format("Subreddit with id = %s was not found.", subredditId)));
//    }
}
