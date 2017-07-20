'use strict';

const ModelIndex = require ('../models');
const UtilsIndex = require ('../utils');
const Address = ModelIndex.getModel('Address');
const CryptUtils = UtilsIndex.CryptUtils;

const AddressController = {};

/**
 *
 * @param {String} name
 * @returns {Promise<Address|undefined>}
 */
AddressController.create = function(number, street, zipcode, town, country) {
    return Address.create({
        number: number,
        street: street,
        zipcode: zipcode,
        town: town,
        country: country
    });
};

/**
 *
 * @param {String} uid
 * @returns {Promise<Address|undefined>}
 */
AddressController.getByUid = function(uid, scope) {
    return Address.scope(scope || 'defaultScope').find({
        where: {
            uid: uid
        }
    });
};

module.exports = AddressController;