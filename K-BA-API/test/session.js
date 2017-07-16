'use strict';

const Session = require('../models').Session;
const User = require('../models').User;

module.exports = {
    getToken: function(email) {
        return Session.find({
            include: [{
                model: User,
                as: 'user',
                where: {
                    email: email
                }
            }]
        }).then((session) => {
            return session.token
        });
    }
}