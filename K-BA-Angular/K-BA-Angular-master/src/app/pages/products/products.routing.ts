import { Routes, RouterModule }  from '@angular/router';

import { Products } from './products.component';
import { List } from './components/list/list.component';
import { Edit } from './components/edit/edit.component';
import { Create } from './components/create/create.component';
import { Categories } from './components/categories/categories.component';

// noinspection TypeScriptValidateTypes
const routes: Routes = [
  {
    path: '',
    component: Products,
    children: [
      { path: '', redirectTo: 'list', pathMatch: 'full'},
      { path: 'list', component: List },
      { path: 'edit/:id', component: Edit },
      { path: 'create', component: Create },
      { path: 'categories/:id', component: Categories },
    ]
  }
];

export const routing = RouterModule.forChild(routes);
