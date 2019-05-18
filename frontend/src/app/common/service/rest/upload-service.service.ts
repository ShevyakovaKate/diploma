import { Injectable } from '@angular/core';
import {HttpClient, HttpEventType} from "@angular/common/http";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class UploadServiceService {

  uploadURL: string = "http://localhost:8080/file";

  constructor(private httpClient: HttpClient) { }

  public upload(file) {
    let formData: FormData = new FormData();
    formData.append('file', file);
    return this.httpClient.post(this.uploadURL, formData);
  }
}
