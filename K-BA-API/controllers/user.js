'use strict';

const ModelIndex = require ('../models');
const UtilsIndex = require ('../utils');
const User = ModelIndex.getModel('User');
const Address = ModelIndex.getModel('Address');

const CryptUtils = UtilsIndex.CryptUtils;

const UserController = {};

/**
 *
 * @param {String} email
 * @param {String} password
 * @returns {Promise<User|undefined>}
 */
UserController.create = function(firstname, lastname, email, password, address, zipcode, city) {
    return CryptUtils.createSalt().then((salt) => {
        return Address.create({
            address : address,
            zipcode : zipcode,
            city : city
        }).then((address) => {
            return User.create({
                email : email,
                password: CryptUtils.hashPassword(salt, password),
                salt : salt,
                address_uid: address.uid
            });
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

/**
 *
 * @param {String} uid
 * @returns {Promise<User|undefined>}
 */
UserController.getByUid = function(uid) {
    return User.find({
        where: {
            uid: uid
        }
    });
};


module.exports = UserController;