import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {
  private baseUrl = 'http://localhost:3002/api/posts';

  constructor(private httpClient: HttpClient) {}

  /**
   * Create a new comment for a post.
   *
   * @param {number} postId - The ID of the post.
   * @param {string} messageRequest - The content of the comment.
   * @return {Observable<{ message: string }>} An observable that emits the response from the server.
   */
  createComment(
    postId: number,
    messageRequest: string
  ): Observable<{ message: string }> {
    return this.httpClient.post<{ message: string }>(
      `${this.baseUrl}/${postId}/comments`,
      messageRequest
    );
  }
}
