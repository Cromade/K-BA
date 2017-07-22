'use strict';

const ModelIndex = require ('../models');
const UtilsIndex = require ('../utils');
const Item = ModelIndex.getModel('Item');
const CryptUtils = UtilsIndex.CryptUtils;

const ItemController = {};

/**
 *
 * @param {String} name
 * @param {String} description
 * @param {Float} price
 * @returns {Promise<Item|undefined>}
 */
ItemController.create = function(name, description,price, manufacturer) {
    return Item.create({
        name: name,
        description: description,
        price: price,
        manufacturer: manufacturer
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
 * @param {String} uid
 * @returns {Promise<Item|undefined>}
 */
ItemController.modify = function(uid, params) {
    return ItemController.getByUid(uid).then((item) => {
        if(item){
            if(params.name) {
                item.name = params.name
           }
           return item.save();
        }
    })
};

/**
 *
 * @returns {Promise<Item|undefined>}
 */
ItemController.listItems = function(search, category_uid) {
    var where= {}
    var include = []

    if(search) {
        where.name = {
            $like:  search + '%'
        }
    }
    if(category_uid) {
        include.push({
            model: ModelIndex.getModel("Category"),
            as : "categories",
            where: {
                uid: category_uid
            }
        })
    }
    return Item.findAll({
        where: where,
        include: include
    });
};

module.exports = ItemController;