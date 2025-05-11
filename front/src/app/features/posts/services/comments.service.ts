import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {
  private baseUrl = 'http://localhost:3002/api/posts';

  constructor(private httpClient: HttpClient) {}

  createComment(
    postId: number,
    messageRequest: string
  ): Observable<{ response: string }> {
    return this.httpClient.post<{ response: string }>(
      `${this.baseUrl}/${postId}/comments`,
      messageRequest
    );
  }
}
