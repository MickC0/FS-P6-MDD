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

  /**
   * Retrieves all posts for a specific user, belonging to topics that the user is subscribed to.
   *
   * @param {number} userId - The ID of the user.
   * @return {Observable<Post[]>} An observable that emits an array of posts.
   */
  getAllPosts(userId: number): Observable<Post[]> {
    return this.httpClient.get<Post[]>(`${this.baseUrl}/user/${userId}/posts`);
  }

  /**
   * Retrieves the details of a specific post, to be displayed on the post detail page.
   *
   * @param {number} postId - The ID of the post.
   * @return {Observable<Post>} An observable that emits the post details.
   */
  getPostDetail(postId: number): Observable<Post> {
    return this.httpClient.get<Post>(`${this.baseUrl}/posts/${postId}`);
  }

  /**
   * Create a new post for a specific topic.
   *
   * @param {Post} post - The post object containing the post details.
   * @param {string} topic - The topic ID for which the post is being created.
   * @return {Observable<{ message: string }>} An observable that emits a response message.
   */
  createPost(post: Post, topic: string): Observable<{ message: string }> {
    return this.httpClient.post<{ message: string }>(
      `${this.baseUrl}/topic/${topic}/post`,
      post
    );
  }

  /**
   * Retrieves the comments for a specific post.
   *
   * @param {number} id - The ID of the post.
   * @return {Observable<Array<Comment>>} An observable that emits an array of comments.
   */
  getComments(id: number): Observable<Array<Comment>> {
    return this.httpClient.get<Array<Comment>>(
      `${this.baseUrl}/posts/${id}/comments`
    );
  }
}
