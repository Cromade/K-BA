'use strict';

const SessionMiddleware = {};
const ModelIndex = require("../models");
const Session = ModelIndex.Session;
const User = ModelIndex.User;

SessionMiddleware.getUser = function() {
    return (req, res, next) => {
        console.log(req.token + " hello")
        if(req.token) {
            Session.find({
                where: {
                    token: req.token
                },
                include: [{
                    model: User,
                    as: 'user'
                }]
            }).then((session) => {
                if(session) {
                    req.user = session.user;
                    next();
                } else {
                    res.status(401).end();
                }
            })
        } else {
            res.status(401).end();
        }
    }
}



module.exports = SessionMiddleware;