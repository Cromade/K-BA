'use strict';

const authTest = require('./auth');
const itemTest = require('./item');
const categoryTest = require('./category');
const groupTest = require('./group');
const listTest = require('./list');

const app = require('../app');

authTest(app);
itemTest(app);
categoryTest(app);
groupTest(app);
listTest(app);




