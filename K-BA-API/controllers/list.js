'use strict';

const ModelIndex = require ('../models');
const UtilsIndex = require ('../utils');
const List = ModelIndex.getModel('List');
const CryptUtils = UtilsIndex.CryptUtils;

const ListController = {};

/**
 *
 * @param {String} name
 * @param {Enum} state
 * @returns {Promise<List|undefined>}
 */
ListController.create = function(name, state, user, fav) {
    return List.create({
        name: name,
        state: state,
        user_id : user.id,
        fav: fav
    });
};

/**
 *
 * @param {String} uid
 * @returns {Promise<List|undefined>}
 */
ListController.getByUid = function(uid) {
    return List.find({
        where: {
            uid: uid
        }
    });
};


module.exports = ListController;