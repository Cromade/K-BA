'use strict';

const ModelIndex = require ('../models');
const UtilsIndex = require ('../utils');
const Notification = ModelIndex.getModel('Notification');
const CryptUtils = UtilsIndex.CryptUtils;

const NotificationController = {};

/**
 *
 * @param {Date} date
 * @param {String} state
 * @param {String} text

 * @returns {Promise<Notification|undefined>}
 */
NotificationController.create = function(date, state, text) {
    return Notification.create({
        date: date,
        state: state,
        text: text
    });
};


module.exports = NotificationController;