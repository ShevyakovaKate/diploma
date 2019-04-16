import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SchemaComponent} from "./schema/schema.component";

const routes: Routes = [
  { path: '', component: SchemaComponent }
 /* { path: 'hero/:id',      component: HeroDetailComponent },
  {
    path: 'heroes',
    component: HeroListComponent,
    data: { title: 'Heroes List' }
  },
  { path: '',
    redirectTo: '/heroes',
    pathMatch: 'full'
  },*/
 /* { path: '**', component: PageNotFoundComponent }*/
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
