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
    /*
    var where = {};
    if(user_id) {
        where["$or"] = {
            "owner_id": user_id,
            "`users.id`": user_id
        }
    }
    return Group.findAll({
        include:[{
            model: ModelIndex.getModel("User"), 
            as: "users"
        }],
        where: where
    });
    */
    return ModelIndex.sequelize.query('SELECT `Group`.`id`, `Group`.`uid`, `Group`.`name`, `Group`.`created_at`, `Group`.`updated_at`, `Group`.`deleted_at`, `Group`.`owner_id`, `users`.`id` AS `users.id`, `users`.`uid` AS `users.uid`, `users`.`firstname` AS `users.firstname`, `users`.`lastname` AS `users.lastname`, `users`.`email` AS `users.email`, `users`.`pseudo` AS `users.pseudo`, `users`.`password` AS `users.password`, `users`.`birthdate` AS `users.birthdate`, `users`.`salt` AS `users.salt`, `users`.`created_at` AS `users.created_at`, `users`.`updated_at` AS `users.updated_at`, `users`.`deleted_at` AS `users.deleted_at`, `users`.`address_id` AS `users.address_id`, `users.GroupUser`.`created_at` AS `users.GroupUser.created_at`, `users.GroupUser`.`updated_at` AS `users.GroupUser.updated_at`, `users.GroupUser`.`group_id` AS `users.GroupUser.group_id`, `users.GroupUser`.`user_id` AS `users.GroupUser.user_id` FROM `Group` AS `Group` LEFT OUTER JOIN (`GroupUser` AS `users.GroupUser` INNER JOIN `User` AS `users` ON `users`.`id` = `users.GroupUser`.`user_id`) ON `Group`.`id` = `users.GroupUser`.`group_id` AND `users`.`deleted_at` IS NULL WHERE (`Group`.`deleted_at` IS NULL AND (`Group`.`owner_id` = ' + user_id +' OR `users.id` = ' + user_id + '))')
};

module.exports = GroupController;