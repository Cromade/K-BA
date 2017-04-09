'use strict';

/**
 * Responsify a sequelize model
 * @param {Model} object
 * @returns {Object}
 */
module.exports = function (object) {
    if (typeof object.responsify === 'function') {
        return object.responsify()
    }
    const json = object.toJSON();
    delete json.id;
    delete json.created_at;
    delete json.updated_at;
    delete json.deleted_at;
    return json;
};