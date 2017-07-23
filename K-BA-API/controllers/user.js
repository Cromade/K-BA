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
UserController.create = function(firstname, lastname, email, pseudo, password, birthdate, address, zipcode, city) {
    return CryptUtils.createSalt().then((salt) => {
        return Address.create({
            address : address,
            zipcode : zipcode,
            city : city
        }).then((address) => {
            return User.create({
                firstname: firstname,
                lastname:lastname,
                email : email,
                password: CryptUtils.hashPassword(salt, password),
                birthdate: birthdate,
                pseudo: pseudo,
                salt : salt,
                address_id: address.id
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
UserController.getByUid = function(uid, scope) {
    return User.scope(scope || 'defaultScope').find({
        where: {
            uid: uid
        }
    });
};


/**
 *
 * @returns {Promise<User|undefined>}
 */
UserController.listUsers = function(search, scope) {
    if(search) {
        return User.scope(scope || 'defaultScope').findAll({
            where: {
                pseudo: {
                    $like:  search + '%'
                }
            }
        })
    }
    return User.findAll();
};

/**
 *
 * @param {String} uid
 * @returns {Promise<User|undefined>}
 */
UserController.modify = function(uid, params) {
    return UserController.getByUid(uid).then((user) => {
        if(user){
            if(params.address) {
                return user.getAddress().then((address) => {
                    if(params.address.address) {
                        address.address = params.address.address
                    }
                    if(params.address.zipcode) {
                        address.zipcode = params.address.zipcode
                    }
                    if(params.address.city) {
                        address.city = params.address.city
                    }
                    return address.save().then(() => {
                        return updateUser(user, params);
                    })
                });
            }
            return updateUser(user, params);
        }
    })
};

function updateUser(user, params) {
    if(params.firstname) {
        user.firstname = params.firstname
    }
    if(params.lastname) {
        user.lastname = params.lastname
    }
    if(params.pseudo) {
        user.pseudo = params.pseudo
    }
    if(params.email) {
        user.email = params.email
    }
    if(params.birthdate) {
        user.birthdate = params.birthdate
    }
    return user.save();
}

module.exports = UserController;