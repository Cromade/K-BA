import { NgModule }      from '@angular/core';
import { CommonModule }  from '@angular/common';
import { FormsModule as AngularFormsModule } from '@angular/forms';
import { AppTranslationModule } from '../../app.translation.module';
import { NgaModule } from '../../theme/nga.module';
import { NgbRatingModule, NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';


import { routing }       from './users.routing';

import { Users } from './users.component';
import { List } from './components/list';
import { Edit } from './components/edit';
import { Create } from './components/create';

import { UsersService } from './users.service';


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
    Users
  ],
  providers: [
    UsersService
  ]
})
export class UsersModule {
}
