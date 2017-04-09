'use strict';

/**
 * The responsifier constructor
 * @param {Function} responsify
 * @constructor
 */
const Responsifier = function (responsify) {
    this.instance = responsify;
};

/**
 *
 * @param {Array<Object>} objects
 * @returns {Array<Object>}
 */
Responsifier.prototype.list = function (objects) {
    const length = objects.length;
    const list = new Array(length);
    for (let i = 0; i < length; i++) {
        list[i] = this.instance(objects[i]);
    }
    return list;
};

module.exports = Responsifier;