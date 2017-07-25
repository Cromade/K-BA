import { NgModule }      from '@angular/core';
import { CommonModule }  from '@angular/common';
import { FormsModule as AngularFormsModule } from '@angular/forms';
import { AppTranslationModule } from '../../app.translation.module';
import { NgaModule } from '../../theme/nga.module';
import { NgbRatingModule, NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';

import { routing }       from './groups.routing';

import { Groups } from './groups.component';
import { List } from './components/list';

import { GroupsService } from './groups.service';


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
    Groups
  ],
  providers: [
    GroupsService
  ]
})
export class GroupsModule {
}
