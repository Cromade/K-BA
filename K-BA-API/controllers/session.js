'use strict';

const ModelIndex = require ('../models');
const UtilsIndex = require ('../utils');
const Session = ModelIndex.getModel('Session');
const CryptUtils = UtilsIndex.CryptUtils;

const SessionController = {};

/**
 *
 * @param {User} user
 * @returns {Promise<Session|undefined>}
 */
SessionController.create = function(user) {
    return CryptUtils.randomToken().then((token) => {
        return Session.create({
            user_id: user.id,
            token: token
        });
    });
};

module.exports = SessionController;