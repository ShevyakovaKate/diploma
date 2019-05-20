import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {UploadServiceService} from "../../common/service/rest/upload-service.service";

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.scss']
})
export class UploadFileComponent implements OnInit {
  @Output() fileEvent: EventEmitter<File> = new EventEmitter();
  file = null;
  private fileUploadService;
  constructor(fileUploadService: UploadServiceService) {
    this.fileUploadService = fileUploadService; }

  ngOnInit(){}

  onFileChange(event) {
    if (event.target.files.length > 0) {
      console.log(event.target.files[0]);
    }
    var reader = new FileReader();
    reader.onload = (event: any) => {
       console.log(event.target.result);
    };

    console.log(reader.readAsDataURL(event.target.files[0]));
    this.fileEvent.emit(event.target.files[0]);


  }

}
