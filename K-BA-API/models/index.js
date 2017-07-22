'use strict';

const fs = require('fs');
const path = require('path');
const Sequelize = require('sequelize');
const basename = path.basename(module.filename);
const ConfigIndex = require('../config');
const config = ConfigIndex.get('database');

const ModelIndex = {};

/**
 * Retrieves a model using his name
 * @param {String} modelName
 * @returns {Model}
 */
ModelIndex.getModel = function (modelName) {
    return this[modelName];
};

ModelIndex.associateScopes = function(modelName) {
    let model = this.getModel(modelName)
    if (model.associateScopes && model._scopeInit === undefined) {
        model.associateScopes(this);
        model._scopeInit = true
    }
}

const sequelize = new Sequelize(config.database, config.username, config.password, config);

fs.readdirSync(__dirname)
    .filter((file) => {
        return (file.indexOf('.') !== 0) && (file !== basename) && (file.slice(-3) === '.js');
    })
    .forEach((file) => {
        const model = sequelize['import'](path.join(__dirname, file));
        ModelIndex[model.name] = model;
    });



Object.keys(ModelIndex).forEach((modelName) => {
  if (ModelIndex[modelName].associate) {
    ModelIndex[modelName].associate(ModelIndex);
  }
  ModelIndex.associateScopes(modelName);
});

ModelIndex.sequelize = sequelize;
ModelIndex.Sequelize = Sequelize;

module.exports = ModelIndex;
