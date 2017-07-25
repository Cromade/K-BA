export const PAGES_MENU = [
  {
    path: 'pages',
    children: [
      {
        path: 'products',
        data: {
          menu: {
            title: 'general.menu.products',
            icon: 'ion-cloud',
            pathMatch: 'prefix',
            selected: false,
            expanded: false,
            order: 0
          }
        }
      },
      {
        path: 'groups',
        data: {
          menu: {
            title: 'general.menu.groups',
            icon: 'ion-cloud',
            pathMatch: 'prefix',
            selected: false,
            expanded: false,
            order: 0
          }
        }
      },
      {
        path: 'categories',
        data: {
          menu: {
            title: 'general.menu.categories',
            icon: 'ion-cloud',
            pathMatch: 'prefix',
            selected: false,
            expanded: false,
            order: 0
          }
        }
      },
      {
        path: 'users',
        data: {
          menu: {
            title: 'general.menu.users',
            icon: 'ion-cloud',
            pathMatch: 'prefix',
            selected: false,
            expanded: false,
            order: 0
          }
        }
      }
    ]
  }
];
