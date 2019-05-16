import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import { UploadFileComponent } from './modules/upload-file/upload-file.component';
import { FfsComponent } from './modules/ffs/ffs.component';
import { PhaseComponent } from './modules/phase/phase.component';
import {SettingComponent} from "./modules/phase/setting/setting.component";
import {ResultsComponent} from "./modules/phase/results/results.component";

@NgModule({
  declarations: [
    AppComponent,
    UploadFileComponent,
    FfsComponent,
    PhaseComponent,
    SettingComponent,
    ResultsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
