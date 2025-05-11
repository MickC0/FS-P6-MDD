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

  getAllTopics(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(this.baseUrl);
  }

  subscribeTopic(id: number) {
    return this.httpClient.post(`${this.baseUrl}/${id}/subscribe`, null, {
      responseType: 'text',
    });
  }

  unsubscribeTopic(id: number) {
    return this.httpClient.delete(`${this.baseUrl}/${id}/subscribe`, {
      responseType: 'text',
    });
  }

  createTopic(topic: Topic): Observable<Topic> {
    return this.httpClient.post<Topic>(
      this.baseUrl,
      topic
    );
  }

}
