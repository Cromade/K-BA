'use strict';

const ResponsifyUtils = require('./responsify');
const CryptUtils = require('./crypt');
const ServerUtils = require('./server');
const RouteUtils = require('./route');

const UtilsIndex = {};

UtilsIndex.ResponsifyUtils = ResponsifyUtils;
UtilsIndex.CryptUtils = CryptUtils;
UtilsIndex.ServerUtils = ServerUtils;
UtilsIndex.RouteUtils = RouteUtils;

module.exports = UtilsIndex;