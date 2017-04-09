'use strict';

const ConfigIndex = {};

/**
 * Current environement
 * @type {String}
 */
ConfigIndex.env = process.env.NODE_ENV || 'development';

/**
 * Retrieves a config object
 * @param {String} type
 * @returns {Object}
 */
ConfigIndex.get = function(type) {
    if(this[type] === undefined) {
        this[type] = require('./' + type + '.json');
    }
    return this[type][this.env];
};

module.exports = ConfigIndex;