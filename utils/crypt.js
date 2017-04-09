'use strict';

const crypto = require('crypto');

const CryptUtils = {};

/**
 * Creates a password salt
 * @returns {Promise<String>}
 */
CryptUtils.createSalt = function () {
    return new Promise((resolve, reject) => {
        crypto.randomBytes(128, (err, buffer) => {
            if (err) {
                return reject(err);
            }
            return resolve(buffer.toString('base64'));
        });
    });
};

/**
 * Hash a password
 * @param {String} salt
 * @param {String} password
 * @returns {String}
 */
CryptUtils.hashPassword = function (salt, password) {
    const hmac = crypto.createHmac('sha1', salt);
    return hmac.update(password).digest('hex');
};

/**
 * Get a random token
 * @returns {Promise<String>}
 */
CryptUtils.randomToken = function () {
    return new Promise((resolve, reject) => {
        crypto.randomBytes(256, (err, buffer) => {
            if (err) {
                return reject(err);
            }
            return resolve(crypto.createHash('sha1').update(buffer).digest('hex'));
        });
    });
};

module.exports = CryptUtils;
