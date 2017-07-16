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


module.exports = AddressController;