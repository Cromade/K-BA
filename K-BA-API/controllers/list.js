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
ListController.create = function(name, state, user, groupId) {
    return List.create({
        name: name,
        state: state,
        user_id : user.id,
        group_id: groupId
    })
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
        },
        include: [{
            model: ModelIndex.getModel('Item'),
            as: 'items'
        }, {
            model: ModelIndex.getModel('Group'),
            as: 'group'
        }]
    });
};

/**
 *
 * @param {String} uid
 * @returns {Promise<List|undefined>}
 */
ListController.modify = function(uid, params) {
    return ListController.getByUid(uid).then((list) => {
        if(list){
            if(params.name) {
                list.name = params.name
           }
            if(params.state) {
                list.state = params.state
           }
           return list.save();
        }
    })
};

/**
 *
 * @param {String} uid
 * @returns {Promise<List|undefined>}
 */
ListController.listLists = function(user_id) {
    return List.findAll({
        where: {
            user_id: user_id
        }
    }).then((userLists) => {
        return List.findAll({
            include: [{
                model: ModelIndex.getModel("Group").scope("minimum"),
                as: "group",
                where: {
                    owner_id: user_id
                }
            }]
        }).then((ownerLists) => {
            return List.findAll({
            include: [{
                model: ModelIndex.getModel("Group").scope("minimum"),
                as: "group",
                include: [{
                    model: ModelIndex.getModel("User").scope("minimum"),
                    as: "users",
                    where: {
                        id: user_id
                    }
                }]
            }]
        }).then((memberLists) => {
            var allLists = userLists.concat(ownerLists, memberLists);
            var unique = []
            allLists.forEach(function(element) {
                if(!unique.some((inner) => {
                    if(inner.uid == element.uid) {
                        return true
                    }
                    return false
                })) {
                    unique.push(element);
                }
            });
            return unique;
        })
        })
    })

};

module.exports = ListController;