import { Routes, RouterModule }  from '@angular/router';
import { Pages } from './pages.component';
import { ModuleWithProviders } from '@angular/core';
import { AuthGuard } from '../auth/_guards/index';
// noinspection TypeScriptValidateTypes

// export function loadChildren(path) { return System.import(path); };

export const routes: Routes = [
  {
    path: 'register',
    loadChildren: 'app/pages/register/register.module#RegisterModule'
  },
  {
    path: 'pages',
    component: Pages,
    children: [
      { path: '', redirectTo: 'products', pathMatch: 'full', canActivate: [AuthGuard] },
      { path: 'products', loadChildren: './products/products.module#ProductsModule', canActivate: [AuthGuard]  },
      { path: 'categories', loadChildren: './categories/categories.module#CategoriesModule', canActivate: [AuthGuard]  },
      { path: 'groups', loadChildren: './groups/groups.module#GroupsModule', canActivate: [AuthGuard]  },
      { path: 'users', loadChildren: './users/users.module#UsersModule', canActivate: [AuthGuard]  },
    ]
  }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);
