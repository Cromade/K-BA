'use strict';

const express = require('express');
const UtilsIndex = require('../utils');
const ControllerIndex = require('../controllers');
const UserController = ControllerIndex.UserController;
const SessionController = ControllerIndex.SessionController;
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;

const router = express.Router();

router.post('/subscribe', (req, res, next) => {
    UserController.create(req.body.email, req.body.password).then((user) => {
       res.json(responsifier.instance(user));
    }).catch(next);
});

router.post('/login', (req, res, next) => {
   UserController.getByEmail(req.body.email).then((user) => {
       if(user && user.authenticate(req.body.password)) {
            SessionController.create(user).then((session) => {
                res.set('Token', session.token);
                res.json(responsifier.instance(user));
           });
       } else {
           res.status(401);
           res.end();
       }
   }).catch(next);
});

module.exports = router;