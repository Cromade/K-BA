import { Component } from '@angular/core';
import { Routes } from '@angular/router';

import { BaMenuService } from '../theme';
import { PAGES_MENU } from './pages.menu';

import { User } from '../auth/_models/index';

@Component({
  selector: 'pages',
  template: `
    <ba-sidebar></ba-sidebar>
    <ba-page-top [currentUser]="currentUser"></ba-page-top>
    <div class="al-main">
      <div class="al-content">
        <ba-content-top></ba-content-top>
        <router-outlet></router-outlet>
      </div>
    </div>
    <footer class="al-footer clearfix">
      <div class="al-footer-main clearfix">
      </div>
    </footer>
    <ba-back-top position="200"></ba-back-top>
    `
})
export class Pages {

  currentUser: User;

   constructor(
    private _menuService: BaMenuService,) 
    {
        this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    }

  ngOnInit() {
    this._menuService.updateMenuByRoutes(<Routes>PAGES_MENU);
  }
}
