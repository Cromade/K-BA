'use strict';

const ServerUtils = {};

/**
 * Normalize the server port
 * @param {String|Number} val
 * @returns {String|Number|Boolean}
 */
ServerUtils.normalizePort = function (val) {
    const port = parseInt(val, 10);
    if (isNaN(port)) {
        return val;
    }
    if (port >= 0) {
        return port;
    }
    return false;
}

module.exports = ServerUtils;
