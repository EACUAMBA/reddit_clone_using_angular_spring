import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './components/header/header.component';
import {SignUpComponent} from './components/sign-up/sign-up.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {LoginComponent} from './components/login/login.component';
import {NgxWebstorageModule} from "ngx-webstorage";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ToastrModule} from "ngx-toastr";
import {HomeComponent} from './components/home/home.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {httpInterceptorProviders} from "./interceptors";
import {PostTileComponent} from './components/post-tile/post-tile.component';
import {VoteButtonComponent} from './components/vote-button/vote-button.component';
import {SideBarComponent} from './components/side-bar/side-bar.component';
import {SubredditSideBarComponent} from './components/subreddit-side-bar/subreddit-side-bar.component';
import {CreateSubredditComponent} from './components/create-subreddit/create-subreddit.component';
import {CreatePostComponent} from './components/create-post/create-post.component';
import {ListSubredditsComponent} from './components/list-subreddits/list-subreddits.component';
import {EditorModule} from "@tinymce/tinymce-angular";
import {ViewPostComponent} from './components/view-post/view-post.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {UserProfileComponent} from './components/user-profile/user-profile.component';
import {CommentComponent} from './components/comment/comment.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SignUpComponent,
    LoginComponent,
    HomeComponent,
    PostTileComponent,
    VoteButtonComponent,
    SideBarComponent,
    SubredditSideBarComponent,
    CreateSubredditComponent,
    CreatePostComponent,
    ListSubredditsComponent,
    ViewPostComponent,
    UserProfileComponent,
    CommentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxWebstorageModule.forRoot(),
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    FontAwesomeModule,
    EditorModule,
    NgbModule
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
