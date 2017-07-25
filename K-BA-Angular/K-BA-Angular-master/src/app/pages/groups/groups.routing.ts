import { Routes, RouterModule }  from '@angular/router';

import { Groups } from './groups.component';
import { List } from './components/list/list.component';

// noinspection TypeScriptValidateTypes
const routes: Routes = [
  {
    path: '',
    component: Groups,
    children: [
      { path: '', redirectTo: 'list', pathMatch: 'full'},
      { path: 'list', component: List },
    ]
  }
];

export const routing = RouterModule.forChild(routes);
