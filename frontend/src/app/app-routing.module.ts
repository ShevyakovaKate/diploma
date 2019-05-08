import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {PhaseComponent} from "./modules/phase/phase.component";
import {FfsComponent} from "./modules/ffs/ffs.component";

const routes: Routes = [
  { path: 'ffs',
    component: FfsComponent,
    data: {
      text: 'ffs'
    }
    },
  {
    path: 'phase',
    component: PhaseComponent,
    data: {
      text: 'phase'
    }
  },
  {
    path: '',
    children: [
      {
        path: '',
        redirectTo: 'phase',
        pathMatch: 'full'
      },
      /*{ path: '**', component: PageNotFoundComponent }*/
    ],
    data: {
      text: 'analysis'
    }
  }
 /* { path: '**', component: PageNotFoundComponent } */
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
