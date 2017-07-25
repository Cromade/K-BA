import { Routes, RouterModule }  from '@angular/router';

import { Users } from './users.component';
import { List } from './components/list/list.component';
import { Edit } from './components/edit/edit.component';
import { Create } from './components/create/create.component';

// noinspection TypeScriptValidateTypes
const routes: Routes = [
  {
    path: '',
    component: Users,
    children: [
      { path: '', redirectTo: 'list', pathMatch: 'full'},
      { path: 'list', component: List },
      { path: 'edit/:id', component: Edit },
      { path: 'create', component: Create },
    ]
  }
];

export const routing = RouterModule.forChild(routes);
