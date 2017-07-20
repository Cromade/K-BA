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
 * @param {String} user_id
 * @returns {Promise<Group|undefined>}
 */
GroupController.listGroups = function(user_id) {
    if(user_id) {
        return Group.findAll({
            include:[{
                model: ModelIndex.getModel("User").scope("minimum"), 
                as: "users",
                required: false
            },{
                model: ModelIndex.getModel("User").scope("minimum"), 
                as: "owner"
            }],
            where: {
                owner_id: user_id
            }
        }).then((groups) => {
            return Group.findAll({
                include:[{
                    model: ModelIndex.getModel("User").scope("minimum"), 
                    as: "users",
                    where: {
                        id: user_id
                    }
                }, {
                    model: ModelIndex.getModel("User").scope("minimum"), 
                    as: "owner",
                    required:false
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