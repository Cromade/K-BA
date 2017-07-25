'use strict';

const authRouter = require('./auth');
const categoryRouter = require('./category');
const itemRouter = require('./item');
const listRouter = require('./list');
const groupRouter = require('./group');
const userRouter = require('./user');
const preferenceRouter = require('./preference');

const RouteIndex = {};

/**
 * Enable all routes to be called
 * @param {Application} app
 */
RouteIndex.attach = function(app) {
    app.use('/auth', authRouter);
    app.use('/category', categoryRouter);
    app.use('/item', itemRouter);
    app.use('/list', listRouter);
    app.use('/group', groupRouter);
    app.use('/user', userRouter);
    app.use('/preference', preferenceRouter);


};

module.exports = RouteIndex;