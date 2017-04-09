'use strict';

const ModelIndex = require ('../models');
const UtilsIndex = require ('../utils');
const Category = ModelIndex.getModel('Category');
const CryptUtils = UtilsIndex.CryptUtils;

const CategoryController = {};

/**
 *
 * @param {String} name
 * @returns {Promise<Category|undefined>}
 */
CategoryController.create = function(name) {
    return Category.create({
        name: name
    });
};

/**
 *
 * @param {String} uid
 * @returns {Promise<Category|undefined>}
 */
CategoryController.getByUid = function(uid) {
    return Category.find({
        where: {
            uid: uid
        }
    });
};

module.exports = CategoryController;