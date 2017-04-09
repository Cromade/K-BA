'use strict';

const ModelIndex = require ('../models');
const UtilsIndex = require ('../utils');
const User = ModelIndex.getModel('User');
const CryptUtils = UtilsIndex.CryptUtils;

const UserController = {};

/**
 *
 * @param {String} email
 * @param {String} password
 * @returns {Promise<User|undefined>}
 */
UserController.create = function(email, password) {
    return CryptUtils.createSalt().then((salt) => {
        return User.create({
            email : email,
            password: CryptUtils.hashPassword(salt, password),
            salt : salt
        });
    });
};

/**
 *
 * @param {String} email
 * @returns {Promise<User|undefined>}
 */
UserController.getByEmail = function(email) {
    return User.find({
        where: {
            email: email
        }
    });
};

module.exports = UserController;