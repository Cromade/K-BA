'use strict';

const RouteUtils = {};

/**
 * Try to find a request params, query or body parameter in the given request
 * @param {Request} req
 * @param {String} param
 * @returns any
 */
RouteUtils.find = function(req, param) {
    let result;
    if(req.params) {
        result = req.params[param];
    }
    if(!result && req.query) {
        result = req.query[param];
    }
    if(!result && req.body) {
        result = req.body[param];
    }
    return result;
};

module.exports = RouteUtils;