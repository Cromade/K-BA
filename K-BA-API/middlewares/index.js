'use strict';

const AuthMiddleware = require('./auth');
const SessionMiddleware = require('./session');

const MiddlewareIndex = {};
MiddlewareIndex.AuthMiddleware = AuthMiddleware;
MiddlewareIndex.SessionMiddleware = SessionMiddleware;

module.exports = MiddlewareIndex;