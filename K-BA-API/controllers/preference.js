'use strict';

const ModelIndex = require ('../models');
const UtilsIndex = require ('../utils');
const Preference = ModelIndex.getModel('Preference');
const CryptUtils = UtilsIndex.CryptUtils;

const PreferenceController = {};

/**
 *
 * @param {String} name
 * @param {Enum} state
 * @returns {Promise<Preference|undefined>}
 */
PreferenceController.create = function(user) {
    return Preference.create({
        user_id: user.id, 
    });
};

/**
 *
 * @param {String} uid
 * @returns {Promise<Preference|undefined>}
 */
PreferenceController.getByUid = function(uid) {
    return Preference.find({
        where: {
            uid: uid
        }
    });
};

module.exports = PreferenceController;