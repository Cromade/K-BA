'use strict';

const ModelIndex = require ('../models');
const UtilsIndex = require ('../utils');
const Plugin = ModelIndex.getModel('Plugin');
const CryptUtils = UtilsIndex.CryptUtils;

const PluginController = {};

/**
 *
 * @param {String} name
 * @param {String} description
 * @returns {Promise<Plugin|undefined>}
 */
PluginController.create = function(name, description) {
    return Plugin.create({
        name: name,
        description: description
    });
};


module.exports = PluginController;