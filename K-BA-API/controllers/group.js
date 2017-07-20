'use strict';

const ModelIndex = require ('../models');
const UtilsIndex = require ('../utils');
const Group = ModelIndex.getModel('Group');
const CryptUtils = UtilsIndex.CryptUtils;

const GroupController = {};

/**
 *
 * @param {String} name
 * @returns {Promise<Group|undefined>}
 */
GroupController.create = function(name) {
    return Group.create({
        name: name
    });
};

/**
 *
 * @param {String} uid
 * @returns {Promise<Group|undefined>}
 */
GroupController.getByUid = function(uid) {
    return Group.find({
        where: {
            uid: uid
        }
    });
};

/**
 * @param {String} user_id
 * @returns {Promise<Group|undefined>}
 */
GroupController.listGroups = function(user_id) {
    var where= {};
    if(user_id) {
        where.user_id = user_id;
    }
    return Group.findAll({
        includes:[{
            model: ModelIndex.getModel("User"), 
            as: "users",
            where: where
        }] 
    });
};

module.exports = GroupController;