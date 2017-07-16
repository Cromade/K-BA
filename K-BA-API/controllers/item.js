'use strict';

const ModelIndex = require ('../models');
const UtilsIndex = require ('../utils');
const Item = ModelIndex.getModel('Item');
const CryptUtils = UtilsIndex.CryptUtils;

const ItemController = {};

/**
 *
 * @param {String} name
 * @param {Float} price
 * @returns {Promise<Item|undefined>}
 */
ItemController.create = function(name, price) {
    return Item.create({
        name: name,
        price: price
    });
};

/**
 *
 * @param {String} uid
 * @returns {Promise<Item|undefined>}
 */
ItemController.getByUid = function(uid) {
    return Item.find({
        where: {
            uid: uid
        }
    });
};


/**
 *
 * @returns {Promise<Item|undefined>}
 */
ItemController.listItems = function(search) {
    if(search) {
        return Item.findAndCountAll({
            where: {
                name: {
                 $like:  search + '%'
                }
            }
        })
    }
    return Item.findAndCountAll();
};

module.exports = ItemController;