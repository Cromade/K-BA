'use strict';

const Responsifier = require('./responsifier');
const sequelizeResponsify = require('./sequelize');

const ResponsifyUtils = {};

ResponsifyUtils.sequelize = new Responsifier(sequelizeResponsify);

module.exports = ResponsifyUtils;