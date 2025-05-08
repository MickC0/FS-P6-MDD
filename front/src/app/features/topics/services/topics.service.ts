import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {Topic} from '../interfaces/Topic.interface';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TopicsService {
  private baseUrl = 'http://localhost:3002/api/topic';

  constructor(private httpClient: HttpClient) {}

  /**
   * Retrieves a list of all available topics.
   *
   * @return {Observable<Topic[]>} An observable that emits an array of topics.
   */
  getAllTopics(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(this.baseUrl);
  }

  /**
   * Add the topic to user's subscriptions.
   *
   * @param {number} id - The ID of the topic to subscribe to.
   * @return {Observable<string>} An observable that emits the response from the server.
   */
  subscribeTopic(id: number) {
    return this.httpClient.post(`${this.baseUrl}/${id}/subscribe`, null, {
      responseType: 'text',
    });
  }

  /**
   * Removes the topic from user's subscriptions.
   *
   * @param {number} id - The ID of the topic to unsubscribe from.
   * @return {Observable<string>} An observable that emits the response from the server.
   */
  unsubscribeTopic(id: number) {
    return this.httpClient.delete(`${this.baseUrl}/${id}/subscribe`, {
      responseType: 'text',
    });
  }
}
