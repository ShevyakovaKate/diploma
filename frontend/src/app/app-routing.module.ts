import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {PhaseComponent} from "./modules/phase/phase.component";
import {FfsComponent} from "./modules/ffs/ffs.component";
import {ResultsComponent} from "./modules/phase/results/results.component";
import {SettingComponent} from "./modules/phase/setting/setting.component";

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
    children: [
      {
        path: 'setting',
        component: SettingComponent
      },
      {
        path: 'results',
        component: ResultsComponent
      }
    ],
    data: {
      text: 'phase'
    }
  },
  { path: '',
    redirectTo: '/phase/setting',
    pathMatch: 'full'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
