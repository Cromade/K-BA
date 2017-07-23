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
GroupController.create = function(name, user_id) {
    return Group.create({
        name: name,
        owner_id: user_id
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
 *
 * @param {String} uid
 * @returns {Promise<Group|undefined>}
 */
GroupController.modify = function(uid, params) {
    return GroupController.getByUid(uid).then((group) => {
        if(group){
            if(params.name) {
                group.name = params.name
            }
            return group.save()
        }
    });
};

/**
 * @param {String} user_id
 * @returns {Promise<Group|undefined>}
 */
GroupController.listGroups = function(user_id) {
    if(user_id) {
        return Group.findAll({
            include:[{
                model: ModelIndex.getModel("User"), 
                as: "users"
            },{
                model: ModelIndex.getModel("User"), 
                as: "owner"
            }],
            where: {
                owner_id: user_id
            }
        }).then((groups) => {
            return Group.findAll({
                include:[{
                    model: ModelIndex.getModel("User"), 
                    as: "users",
                    where: {
                        id: user_id
                    }
                }, {
                    model: ModelIndex.getModel("User"), 
                as: "owner"
                }]
            }).then((others) => {
                return groups.concat(others);
            });
        })
    }
    return Group.findAll({
        include:[{
            model: ModelIndex.getModel("User"), 
            as: "users"
        }],
    });
};

module.exports = GroupController;