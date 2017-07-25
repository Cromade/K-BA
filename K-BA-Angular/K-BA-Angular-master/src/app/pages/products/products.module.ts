import { NgModule }      from '@angular/core';
import { CommonModule }  from '@angular/common';
import { FormsModule as AngularFormsModule } from '@angular/forms';
import { AppTranslationModule } from '../../app.translation.module';
import { NgaModule } from '../../theme/nga.module';
import { NgbRatingModule, NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';


import { routing }       from './products.routing';

import { Products } from './products.component';
import { List } from './components/list';
import { Edit } from './components/edit';
import { Create } from './components/create';
import { Categories } from './components/categories';

import { ProductsService } from './products.service';
import { CategoriesService } from '../categories/categories.service';



@NgModule({
  imports: [
    CommonModule,
    AngularFormsModule,
    AppTranslationModule,
    NgaModule,
    NgbRatingModule,
    NgbDropdownModule,
    routing
  ],
  declarations: [
    List,
    Edit,
    Create,
    Categories,
    Products
  ],
  providers: [
    ProductsService,
    CategoriesService
  ]
})
export class ProductsModule {
}
