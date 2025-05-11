import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {Post} from '../interfaces/Post.interface';
import {Comment} from '../interfaces/Comment.interface';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PostsService {

  private baseUrl = 'http://localhost:3002/api';

  constructor(private httpClient: HttpClient) {}

  getAllPosts(userId: number): Observable<Post[]> {
    return this.httpClient.get<Post[]>(`${this.baseUrl}/user/${userId}/posts`);
  }

  getPostDetail(postId: number): Observable<Post> {
    return this.httpClient.get<Post>(`${this.baseUrl}/posts/${postId}`);
  }

  createPost(post: Post): Observable<Post> {
    return this.httpClient.post<Post>(
      `${this.baseUrl}/posts`,
      post
    );
  }

  getComments(id: number): Observable<Array<Comment>> {
    return this.httpClient.get<Array<Comment>>(
      `${this.baseUrl}/posts/${id}/comments`
    );
  }
}
