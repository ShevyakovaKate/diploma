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
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { TableComponent } from './modules/phase/setting/table/table.component';
import { FfsSettingComponent } from './modules/ffs/ffs-setting/ffs-setting.component';
import { FfsResultComponent } from './modules/ffs/ffs-result/ffs-result.component';
import { FfsTableComponent } from './modules/ffs/ffs-setting/ffs-table/ffs-table.component';

@NgModule({
  declarations: [
    AppComponent,
    UploadFileComponent,
    FfsComponent,
    PhaseComponent,
    SettingComponent,
    ResultsComponent,
    TableComponent,
    FfsSettingComponent,
    FfsResultComponent,
    FfsTableComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
