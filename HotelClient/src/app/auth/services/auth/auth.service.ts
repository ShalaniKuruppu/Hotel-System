//This Angular service handles user registration by sending the signup form data to your Spring Boot backend using HTTP POST.
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';

//backend URL
const BASIC_URL ="http://localhost:8080/"

@Injectable({
  providedIn: 'root'
})
export class AuthService {


  constructor(private http: HttpClient) { }

  register(signupRequest: any): Observable<any>{
    return this.http.post(BASIC_URL + "api/auth/signup", signupRequest);
  }
}