import { Injectable } from '@angular/core';
import {HttpClient, HttpEventType} from "@angular/common/http";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class UploadServiceService {

  uploadURL: string = "/api/file";

  constructor(private httpClient: HttpClient) { }

  public upload(file) {
    const uploadData = new FormData();
    uploadData.append('file', file, file.name);
    return this.httpClient.post(this.uploadURL, uploadData);
  }
}
